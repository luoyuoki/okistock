from OkiStockSpider.spiders.base import BaseSpider


class FutuNoproxySpider(BaseSpider):
    name = 'futu_noproxy'
    custom_settings = {'PROXY_ENABLED': 0, 'DOWNLOAD_DELAY': 5}
