from scrapy import Spider
from scrapy import Request
from scrapy.exceptions import CloseSpider

from OkiStockSpider.items import StockItemLoader, StockInfoItem, StockQuoteItem
from datetime import datetime
import time
import re
import json


class BaseSpider(Spider):
    allowed_domains = ['futunn.com']

    HK_BASE_URL = 'https://www.futunn.com/quote/stock?m=hk&code={}'
    hk_urls = [HK_BASE_URL.format('00700'),
               HK_BASE_URL.format('01810'),
               HK_BASE_URL.format('00175'),
               HK_BASE_URL.format('01093'),
               HK_BASE_URL.format('01548'),
               HK_BASE_URL.format('02382'),
               HK_BASE_URL.format('02018'),
               HK_BASE_URL.format('01918'),
               HK_BASE_URL.format('02318'),
               HK_BASE_URL.format('00763'),
               HK_BASE_URL.format('08083'),
               HK_BASE_URL.format('00388'),
               ]

    US_BASE_URL = 'https://www.futunn.com/quote/stock?m=us&code={}'
    us_urls = [US_BASE_URL.format('BABA'),
               US_BASE_URL.format('JD'),
               US_BASE_URL.format('WB'),
               US_BASE_URL.format('BIDU'),
               US_BASE_URL.format('MOMO'),
               US_BASE_URL.format('EDU'),
               US_BASE_URL.format('TAL'),
               US_BASE_URL.format('IQ'),
               US_BASE_URL.format('PPDF'),
               US_BASE_URL.format('PDD'),
               US_BASE_URL.format('TSLA'),
               US_BASE_URL.format('AAPL'),
               US_BASE_URL.format('AMZN'),
               US_BASE_URL.format('GOOG'),
               US_BASE_URL.format('MSFT'),
               US_BASE_URL.format('FB'),
               US_BASE_URL.format('NFLX'),
               US_BASE_URL.format('SQ'),
               US_BASE_URL.format('SHOP'),
               US_BASE_URL.format('NVDA'),
               US_BASE_URL.format('AMD')
               ]

    def __init__(self, category=None, *args, **kwargs):
        super(BaseSpider, self).__init__(*args, **kwargs)
        if category == 'hk':
            self.start_urls = self.hk_urls
        elif category == 'us':
            self.start_urls = self.us_urls
        else:
            raise CloseSpider('spider category is wrong')
        setattr(self, 'scope', category)

    def start_requests(self):
        for url in self.start_urls:
            yield Request(url, meta={'download_timeout': 3}, dont_filter=True)

    def parse(self, response):
        item_loader = StockItemLoader(item=StockInfoItem(), response=response)
        item_loader.add_xpath('stock_name', '//h1/@title')
        item_loader.add_xpath('current_price', '//*[@id="basicQuote"]/div[2]/span[1]/text()')
        item_loader.add_xpath('change_price', '//*[@id="basicQuote"]/div[2]/span[2]/text()')
        item_loader.add_xpath('change_percent', '//*[@id="basicQuote"]/div[2]/span[3]/text()')
        item_loader.add_xpath('highest_price', '//*[@id="basicQuote"]/div[3]/ul/li[1]/p[1]/span/text()')
        item_loader.add_xpath('lowest_price', '//*[@id="basicQuote"]/div[3]/ul/li[1]/p[2]/span/text()')
        item_loader.add_xpath('begin_price', '//*[@id="basicQuote"]/div[3]/ul/li[2]/p[1]/span/text()')
        item_loader.add_xpath('last_price', '//*[@id="basicQuote"]/div[3]/ul/li[2]/p[2]/text()')
        item_loader.add_xpath('turnover_asset', '//*[@id="basicQuote"]/div[3]/ul/li[3]/p[1]/text()')
        item_loader.add_xpath('turnover_nums', '//*[@id="basicQuote"]/div[3]/ul/li[3]/p[2]/text()')
        item_loader.add_xpath('pe_ratio', '//*[@id="basicQuote"]/div[3]/ul/li[4]/p[1]/text()')
        item_loader.add_xpath('market_value', '//*[@id="basicQuote"]/div[3]/ul/li[4]/p[2]/text()')
        item_loader.add_xpath('turnover_rate', '//*[@id="basicQuote"]/div[3]/ul/li[5]/p[1]/text()')
        item_loader.add_xpath('shock_range', '//*[@id="basicQuote"]/div[3]/ul/li[5]/p[2]/text()')
        item_loader.add_value('update_time', datetime.now())

        stock_item = item_loader.load_item()
        yield stock_item

        security_id = re.search(r'\.securityId.*\'(.*?)\'', response.text, re.I).group(1)
        curr_time = int(time.time() * 1000)

        yield Request('https://www.futunn.com/trade/quote-minute-v2?security_id={}&_={}'.format(security_id, curr_time),
                      meta={'stock_name': stock_item['stock_name'], 'download_timeout': 3}, dont_filter=True,
                      callback=self.parse_quote_minute)

    def parse_quote_minute(self, response):
        try:
            quote_json = json.loads(response.text)
            quote_list = quote_json['data']['list']
        except Exception as e:
            print(e)
            return

        quote_item = StockQuoteItem()
        quote_item['stock_name'] = response.meta.get('stock_name')
        quote_item['stock_quote'] = quote_list
        yield quote_item
