const http = require('../../utils/http.js')
const util = require('../../utils/util.js')

var intervalId = 0

Page({

    data: {
        stockList: [],
        isTrading: false,

        marqueeContent: '',
        marqueePace: 2,
        marqueeDistance: 0,
        marqueeFontSize: 28,
        orientation: 'left',
        interval: 20
    },

    onLoad: function (options) {

    },

    onReady: function () {
        this.requestStockList()
    },

    onShow: function () {
        if (this.data.marqueeContent && intervalId != 0) {
            this.marqueeRun()
        }
    },

    requestStockList:function(){
        var data = {}
        http.doGet('/stock/us', data, this.processStockList)
    },

    processStockList: function (data) {
        if (data.success) {
            this.setData({
                stockList: data.stockList,
                isTrading: data.trading
            })

            if (this.data.isTrading) {
                this.setData({
                    marqueeContent: '美股市场正在交易中'
                })

                var contentLength = this.data.marqueeContent.length * this.data.marqueeFontSize
                var windowWidth = wx.getSystemInfoSync().windowWidth
                this.setData({
                    contentLength: contentLength,
                    windowWidth: windowWidth,
                    marqueeDistance: windowWidth + contentLength,
                });

                this.marqueeRun()
            }
        } else {
            util.showServerErrorToast()
        }
    },

    onTapStockItem: function (event) {
        var stockId = event.currentTarget.dataset.stockId
        wx.navigateTo({
            url: '../exchange/exchange?stockId=' + stockId + '&trading=' + this.data.isTrading,
        })
    },

    onHide: function () {
        clearInterval(intervalId)
    },

    onUnload: function () {
        clearInterval(intervalId)
        intervalId = 0
    },

    onPullDownRefresh: function () {
        this.requestStockList()
    },

    marqueeRun: function () {
        clearInterval(intervalId)
        intervalId = setInterval(() => {
            if (-this.data.marqueeDistance < this.data.contentLength) {
                this.setData({
                    marqueeDistance: this.data.marqueeDistance - this.data.marqueePace
                })
            } else {
                this.setData({
                    marqueeDistance: this.data.windowWidth + this.data.contentLength
                })
                clearInterval(intervalId)
                this.marqueeRun()
            }
        }, this.data.interval)
    }
})