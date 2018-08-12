import time
from urllib import request
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor
from fake_useragent import UserAgent
import re
import urllib
import logging

logger = logging.getLogger(__name__)


class ProxyProvider:

    def __init__(self):
        self.userAgent = UserAgent()
        self.check_url = 'http://httpbin.org/ip'
        self.proxy_url = ''
        self.grades = ['2', '3', '4'] * 2
        self.error_times = 0

    def _get_source_url(self):
        self.proxy_url = 'http://www.66ip.cn/nmtq.php?getnum={}&isp=0&anonymoustype={}&start=&ports=&export=&ipaddress=&area=0&proxytype=1&api=66ip'.format(
            '30', self.grades.pop())
        return self.proxy_url

    def get_proxy_list(self):
        if self.grades:
            source_url = self._get_source_url()
            logger.debug('soure url:{}'.format(source_url))
            page = self._load_page(source_url)

            if page and self.error_times <= 5:
                soup = BeautifulSoup(page, 'html5lib')
                page_title = soup.title.string
                if '成功' in page_title:
                    logger.debug('fetching proxy')
                    proxies = [item for item in soup.stripped_strings if re.match('\d', item)]
                    if proxies and len(proxies) <= 10:
                        self.error_times += 1
                        time.sleep(5)
                        return self.get_proxy_list()

                    valid_proxies = []
                    executor = ThreadPoolExecutor(10)
                    for ip in executor.map(self._check_ip, (proxy for proxy in proxies)):
                        if ip:
                            valid_proxies.append(ip)

                    if len(valid_proxies) > 10:
                        logger.debug(valid_proxies)
                        logger.debug(len(valid_proxies))
                        self.error_times = 0
                        return valid_proxies
                    else:
                        self.error_times += 1
                        time.sleep(8)
                        self.get_proxy_list()
                else:
                    logger.debug('proxy page parse error!! ')
                    return None
            else:
                time.sleep(5)
                self.get_proxy_list()
        else:
            logger.debug('no proxy use!!')
            return None

    def _load_page(self, url):
        headers = {'User-Agent': self.userAgent.random}
        url_with_headers = request.Request(url, headers=headers)
        try:
            response = request.urlopen(url_with_headers, timeout=5)
        except Exception:
            return None
        if response.code != 200:
            return None

        page = response.read()
        charset = self._parse_charset(page)

        try:
            page = page.decode(charset)
        except Exception:
            return None
        return page

    def _parse_charset(self, page):
        content = str(page)
        meta = re.search(r'<meta(.*?)>', content, re.I)
        if meta:
            group = meta.group()
            temp = re.search(r'charset=(.*?)[\"\']', group, re.I)
            if temp:
                charset = temp.groups()[0]
                return charset
        return 'utf-8'

    def _check_ip(self, ip):
        ip = {'https': ip}
        proxy = urllib.request.ProxyHandler(ip)
        opener = urllib.request.build_opener(proxy)
        headers = {'User-Agent': self.userAgent.random}
        url_with_headers = urllib.request.Request(self.check_url, headers=headers)

        try:
            response = opener.open(url_with_headers, timeout=5)
            if response.code == 200:
                logger.debug('checked ip:{}'.format(ip))
                page = response.read().decode('utf-8')
                data_dict = eval(page)
                data = data_dict['origin']
                if data.find(',') == -1:
                    return ip
                else:
                    return None

        except Exception:
            return None
