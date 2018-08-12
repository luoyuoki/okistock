# -*- coding: utf-8 -*-

# Scrapy settings for OkiStockSpider project
#
# For simplicity, this file contains only settings considered important or
# commonly used. You can find more settings consulting the documentation:
#
#     https://doc.scrapy.org/en/latest/topics/settings.html
#     https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#     https://doc.scrapy.org/en/latest/topics/spider-middleware.html

BOT_NAME = 'OkiStockSpider'

SPIDER_MODULES = ['OkiStockSpider.spiders']
NEWSPIDER_MODULE = 'OkiStockSpider.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
# USER_AGENT = 'OkiStockSpider (+http://www.yourdomain.com)'

# Obey robots.txt rules
ROBOTSTXT_OBEY = False

# Configure maximum concurrent requests performed by Scrapy (default: 16)
CONCURRENT_REQUESTS = 10

# Configure a delay for requests for the same website (default: 0)
# See https://doc.scrapy.org/en/latest/topics/settings.html#download-delay
# See also autothrottle settings and docs

# The download delay setting will honor only one of:
# CONCURRENT_REQUESTS_PER_DOMAIN = 16
# CONCURRENT_REQUESTS_PER_IP = 16

# Disable cookies (enabled by default)
COOKIES_ENABLED = False

# Disable Telnet Console (enabled by default)
# TELNETCONSOLE_ENABLED = False

# Override the default request headers:
DEFAULT_REQUEST_HEADERS = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
    'Accept-Language': 'zh-CN,zh;q=0.9',
    'Accept-Encoding': 'gzip, deflate, br',
    'DNT': '1',
    'Host': 'www.futunn.com',
    'Connection': 'keep-alive'
}

# Enable or disable spider middlewares
# See https://doc.scrapy.org/en/latest/topics/spider-middleware.html
# SPIDER_MIDDLEWARES = {
# 'OkiStockSpider.middlewares.OkistockspiderSpiderMiddleware': 543,
# }

# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
DOWNLOADER_MIDDLEWARES = {
    # 'OkiStockSpider.middlewares.OkistockspiderDownloaderMiddleware': 543,
    'scrapy.contrib.downloadermiddleware.retry.RetryMiddleware': None,
    'OkiStockSpider.middlewares.FakeUserAgentMiddleware': 543,
    'OkiStockSpider.middlewares.MyRetryMiddleware': 544,
    'OkiStockSpider.middlewares.ProxyMiddleware': 545

}

# Enable or disable extensions
# See https://doc.scrapy.org/en/latest/topics/extensions.html
# EXTENSIONS = {
#    'scrapy.extensions.telnet.TelnetConsole': None,
# }

# Configure item pipelines
# See https://doc.scrapy.org/en/latest/topics/item-pipeline.html
ITEM_PIPELINES = {
    # 'OkiStockSpider.pipelines.OkistockspiderPipeline': 300,
    'OkiStockSpider.pipelines.OkiStockPipeline': 300

}

# Enable and configure the AutoThrottle extension (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/autothrottle.html
AUTOTHROTTLE_ENABLED = True
# The initial download delay
# AUTOTHROTTLE_START_DELAY = 5
# The maximum download delay to be set in case of high latencies
# AUTOTHROTTLE_MAX_DELAY = 60
# The average number of requests Scrapy should be sending in parallel to
# each remote server
# AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
# Enable showing throttling stats for every response received:
# AUTOTHROTTLE_DEBUG = False

# Enable and configure HTTP caching (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html#httpcache-middleware-settings
# HTTPCACHE_ENABLED = True
# HTTPCACHE_EXPIRATION_SECS = 0
# HTTPCACHE_DIR = 'httpcache'
# HTTPCACHE_IGNORE_HTTP_CODES = []
# HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'

CLOSESPIDER_TIMEOUT = 8100
DOWNLOAD_TIMEOUT = 30

MYSQL_DB_NAME = 'okistock'
MYSQL_HOST = 'localhost'
MYSQL_PORT = 3306
MYSQL_USER = ''
MYSQL_PASSWORD = ''

REDIS_HOST = 'localhost'
REDIS_PORT = 6379
REDIS_PASSWORD = ''

SQL_DATETIME_FORMAT = '%Y-%m-%d %H:%M:%S'

PROXY_ENABLED = 1
DOWNLOAD_DELAY = 0
# PROXY_ENABLED = 0
# DOWNLOAD_DELAY = 5

RETRY_ENABLED = True
RETRY_TIMES = 10
RETRY_HTTP_CODES = [403, 407, 408, 503, 504]
