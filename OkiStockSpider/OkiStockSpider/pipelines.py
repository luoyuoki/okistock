from twisted.enterprise import adbapi
import MySQLdb
import MySQLdb.cursors
import redis
from decimal import Decimal
from OkiStockSpider.items import StockInfoItem, StockQuoteItem
import logging

logger = logging.getLogger(__name__)


class OkiStockPipeline:
    def open_spider(self, spider):
        db_params = dict(
            host=spider.settings.get('MYSQL_HOST'),
            db=spider.settings.get('MYSQL_DB_NAME'),
            user=spider.settings.get('MYSQL_USER'),
            passwd=spider.settings.get('MYSQL_PASSWORD'),
            charset='utf8',
            cursorclass=MySQLdb.cursors.DictCursor,
            use_unicode=True,
        )
        self.dbpool = adbapi.ConnectionPool('MySQLdb', **db_params)
        self.redispool = redis.ConnectionPool(host=spider.settings.get('REDIS_HOST'),
                                              port=spider.settings.get('REDIS_PORT'),
                                              password=spider.settings.get('REDIS_PASSWORD'),
                                              decode_responses=True)
        self.redis = redis.Redis(connection_pool=self.redispool)

    def close_spider(self, spider):
        scope = getattr(spider, 'scope')
        message = {'spider': 'stop', 'scope': scope}
        self.redis.publish('notification', str(message))
        self.dbpool.close()
        self.redispool.disconnect()

    def process_item(self, item, spider):
        if isinstance(item, StockInfoItem):
            message = {'stock_name': item['stock_name'], 'current_price': item['current_price']}
            self.redis.publish('stock', str(message))
            process = self.dbpool.runInteraction(self.update_data, item)
            process.addErrback(self.handle_error, item, spider)

        elif isinstance(item, StockQuoteItem):
            for data in self.redis.scan_iter():
                if item['stock_name'] in data:
                    quotes = item['stock_quote']

                    data_dict = dict(self.redis.hscan_iter(data))
                    logger.debug(data_dict)

                    quote_price = Decimal(data_dict['quote_price']) * 1000
                    commit_time = int(data_dict['commit_time'])
                    quote_nums = data_dict['quote_nums']
                    order_type = data_dict['order_type']
                    order_id = data_dict['order_id']
                    stock_id = data_dict['stock_id']
                    openid = data_dict['openid']
                    stock_scope = data_dict['stock_scope']

                    for quote in quotes:
                        if commit_time <= quote['time']:
                            if order_type == '0':
                                if quote_price >= quote['price']:
                                    exchange_price = quote['price']
                                    exchange_time = quote['time']
                                    logger.debug('{} buy:{}--{}'.format(openid, exchange_price, exchange_time))

                                    message = {'order_id': order_id, 'exchange_price': str(exchange_price),
                                               'exchange_time': str(exchange_time), 'stock_id': stock_id,
                                               'openid': openid, 'quote_price': data_dict['quote_price'],
                                               'quote_nums': quote_nums, 'stock_scope': stock_scope,
                                               'order_type': order_type, 'stock_name': item['stock_name']}
                                    self.redis.publish('exchange', str(message))
                                    break
                            elif order_type == '1':
                                if quote_price <= quote['price']:
                                    exchange_price = quote['price']
                                    exchange_time = quote['time']
                                    logger.debug('{} sell:{}--{}'.format(openid, exchange_price, exchange_time))

                                    message = {'order_id': order_id, 'exchange_price': str(exchange_price),
                                               'exchange_time': str(exchange_time), 'stock_id': stock_id,
                                               'openid': openid, 'quote_nums': quote_nums, 'stock_scope': stock_scope,
                                               'order_type': order_type, 'stock_name': item['stock_name']}
                                    self.redis.publish('exchange', str(message))
                                    break

        # return item

    def handle_error(self, failure, item, spider):
        logger.debug('OkiStockPipeline handle_error:'.format(failure))

    def update_data(self, cursor, item):
        sql, params = item.update_data_sql()
        cursor.execute(sql, params)
