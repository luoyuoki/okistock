# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals
from fake_useragent import UserAgent
from scrapy.downloadermiddlewares.retry import RetryMiddleware
from scrapy.exceptions import CloseSpider
from scrapy.utils.response import response_status_message

from OkiStockSpider.util.proxy_provider import ProxyProvider
import random
import logging

logger = logging.getLogger(__name__)


class OkistockspiderSpiderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_spider_input(self, response, spider):
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, response, result, spider):
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, dict or Item objects.
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Response, dict
        # or Item objects.
        pass

    def process_start_requests(self, start_requests, spider):
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        for r in start_requests:
            yield r

    def spider_opened(self, spider):
        spider.logger.info('Spider opened: %s' % spider.name)


class OkistockspiderDownloaderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        return None

    def process_response(self, request, response, spider):
        # Called with the response returned from the downloader.

        # Must either;
        # - return a Response object
        # - return a Request object
        # - or raise IgnoreRequest
        return response

    def process_exception(self, request, exception, spider):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        logger.debug('OkistockspiderDownloaderMiddleware process_exception:{}'.format(exception))
        pass

    def spider_opened(self, spider):
        logger.debug('Spider opened: %s' % spider.name)


class FakeUserAgentMiddleware:
    @classmethod
    def from_crawler(cls, crawler):
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def spider_opened(self, spider):
        spider.ua = UserAgent()

    def process_request(self, request, spider):
        request.headers['User-Agent'] = spider.ua.random


class ProxyMiddleware:

    def __init__(self):
        self.proxy_provider = ProxyProvider()
        self.first = True

    @classmethod
    def from_crawler(cls, crawler):
        proxy_enabled = crawler.settings.get('PROXY_ENABLED', 0)
        if proxy_enabled:
            s = cls()
            crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
            return s

    def spider_opened(self, spider):
        logger.debug('ProxyMiddleware init')
        spider.proxy_list = self.proxy_provider.get_proxy_list()
        if not spider.proxy_list:
            raise CloseSpider('无代理可用，强制停止')
        spider.good_proxies = []

    def process_request(self, request, spider):
        logger.debug('start crawl')

        if not self.first and spider.good_proxies:
            logger.debug('good_proxies:{}'.format(spider.good_proxies))
            proxy = random.choice(spider.good_proxies)
            request.meta['proxy'] = proxy

        elif spider.proxy_list:
            self.first = False

            if len(spider.proxy_list) > 5:
                proxy = random.choice(spider.proxy_list)

                for k, v in proxy.items():
                    ip = '{}://{}'.format(k, v)

                logger.debug('proxy ip is {}'.format(ip))
                if ip:
                    request.meta['proxy'] = ip
            else:
                spider.proxy_list = self.proxy_provider.get_proxy_list()
                return request
        else:
            raise CloseSpider('no proxy use，force stop!')

    def process_response(self, request, response, spider):
        if response.status == 200:
            proxy = request.meta.get('proxy', False)
            if proxy and proxy not in spider.good_proxies:
                spider.good_proxies.append(proxy)

        return response


class MyRetryMiddleware(RetryMiddleware):

    def delete_proxy(self, proxy, spider):
        if proxy:
            logger.debug('retry remove ip:{}'.format(proxy))
            if spider.good_proxies:
                spider.good_proxies = [ip for ip in spider.good_proxies if ip != proxy]
            if spider.proxy_list:
                address = proxy.split('://')[1]
                spider.proxy_list = [ip for ip in spider.proxy_list if ip['https'] != address]

    def process_response(self, request, response, spider):

        if request.meta.get('dont_retry', False):
            return response

        if response.status in self.retry_http_codes:
            logger.debug('retry because status:{}'.format(response.status))
            reason = response_status_message(response.status)
            self.delete_proxy(request.meta.get('proxy', False), spider)
            return self._retry(request, reason, spider) or response
        return response

    def process_exception(self, request, exception, spider):
        # if isinstance(exception, self.EXCEPTIONS_TO_RETRY) and not request.meta.get('dont_retry', False):
        logger.debug('retry because exception:{}'.format(exception))
        self.delete_proxy(request.meta.get('proxy', False), spider)
        return self._retry(request, exception, spider)
