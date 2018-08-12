from OkiStockSpider.spiders.base import BaseSpider


class FutuSpider(BaseSpider):
    name = 'futu'
    custom_settings = {'PROXY_ENABLED': 1, 'DOWNLOAD_DELAY': 0}
