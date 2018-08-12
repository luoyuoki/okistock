# -*- coding: utf-8 -*-

import sys
import os
from scrapy import cmdline


sys.path.append(os.path.dirname(os.path.abspath(__file__)))
cmdline.execute(['scrapy', 'crawl', 'futu', '-a', 'category=hk'])
# cmdline.execute(['scrapy', 'crawl', 'futu', '-a', 'category=us'])
# cmdline.execute(['scrapy', 'crawl', 'futu_noproxy', '-a', 'category=us'])

# execute(["scrapy", "crawl", "test"])
