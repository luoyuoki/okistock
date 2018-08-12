from scrapy import Item, Field
from scrapy.loader import ItemLoader
from scrapy.loader.processors import MapCompose, TakeFirst
from OkiStockSpider.settings import SQL_DATETIME_FORMAT


def handle_date_format(date):
    return date.strftime(SQL_DATETIME_FORMAT)


def handle_two_digit(value):
    return '{:.2f}'.format(float(value))


def handle_extract_info(value):
    return value.split('：')[-1]


class StockItemLoader(ItemLoader):
    default_output_processor = TakeFirst()


class StockInfoItem(Item):
    stock_name = Field()
    current_price = Field(
        input_processor=MapCompose(handle_two_digit)
    )
    change_price = Field(
        input_processor=MapCompose(handle_two_digit)
    )
    change_percent = Field()
    highest_price = Field(
        input_processor=MapCompose(handle_two_digit)
    )
    lowest_price = Field(
        input_processor=MapCompose(handle_two_digit)
    )
    begin_price = Field(
        input_processor=MapCompose(handle_two_digit)
    )
    last_price = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    turnover_asset = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    turnover_nums = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    pe_ratio = Field()
    market_value = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    turnover_rate = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    shock_range = Field(
        input_processor=MapCompose(handle_extract_info)
    )
    pe_ratio = Field(
        input_processor=MapCompose(handle_extract_info, handle_two_digit)
    )
    update_time = Field(
        input_processor=MapCompose(handle_date_format)
    )

    def update_data_sql(self):
        sql = 'UPDATE oki_stock SET current_price=%s,change_percent=%s,change_price=%s,highest_price=%s,lowest_price=%s,begin_price=%s,last_price=%s,turnover_asset=%s,turnover_nums=%s,pe_ratio=%s,market_value=%s,turnover_rate=%s,shock_range=%s,update_time=%s WHERE stock_name=%s'

        params = (self['current_price'], self['change_percent'], self['change_price'], self['highest_price'],
                  self['lowest_price'], self['begin_price'], self['last_price'], self['turnover_asset'],
                  self['turnover_nums'], self['pe_ratio'], self['market_value'], self['turnover_rate'],
                  self['shock_range'], self['update_time'],
                  self['stock_name'])

        return sql, params


class StockQuoteItem(Item):
    stock_name = Field()
    stock_quote = Field()
