const App = getApp()
const Http = require('../../utils/http.js')
const Util = require('../../utils/util.js')

var intervalId = 0

Page({

    data: {
        openid: '',
        nickName: '',
        avatarUrl: '',
        hkAssets: '',
        hkRestDollar: '',
        hkProfitAmount: '',
        hkProfitPercent: '',
        usAssets: '',
        usRestDollar: '',
        usProfitAmount: '',
        usProfitPercent: '',
        hkFrozenCapital: '',
        usFrozenCapital: '',
        hkRise: false,
        usRise: false,

        message: '',
        stockHolderList: [''],
        newOrderList: [''],
        successOrderList: [''],
        hkTrading: false,
        usTrading: false,

        currentTab: 0,
        tabArr: ['持仓', '今日订单', '交易历史'],

        marqueeContent: '',
        marqueePace: 2, //滚动速度
        marqueeDistance: 0, //初始滚动距离
        marqueeFontSize: 28,
        orientation: 'left', //滚动方向
        interval: 20 // 时间间隔
    },

    onLoad: function(options) {
        var openid = App.globalData.openid
        if (!openid) {
            openid = wx.getStorageSync('openid')
            App.globalData.openid = openid
        }

    },

    onReady: function() {
        this.requestMyData()
    },

    requestMyData: function() {
        var data = {
            'openid': App.globalData.openid
        }
        Http.doGet('/my/main', data, this.processMyData)
    },

    processMyData: function(response) {
        if (response.code === App.RESP_OK) {
            var data = response.data
            this.setData({
                openid: data.user.openid,
                nickName: data.user.nickName,
                avatarUrl: data.user.avatarUrl,
                hkAssets: data.user.hkAssets,
                hkRestDollar: data.user.hkRestDollar,
                hkProfitAmount: data.user.hkProfitAmount,
                hkProfitPercent: data.user.hkProfitPercent,
                hkRise: data.user.hkRise,
                usAssets: data.user.usAssets,
                usRestDollar: data.user.usRestDollar,
                usProfitAmount: data.user.usProfitAmount,
                usProfitPercent: data.user.usProfitPercent,
                usRise: data.user.usRise,
                hkFrozenCapital: data.user.hkFrozenCapital,
                usFrozenCapital: data.user.usFrozenCapital,

                stockHolderList: data.holderList,
                marqueeContent: data.notification.message,

                hkTrading: data.tradingFlag.hkTrading,
                usTrading: data.tradingFlag.usTrading
            })

            var index = this.data.currentTab
            if (index == 1) {
                var data = {
                    'openid': this.data.openid,
                    'needUpdateAssets': false
                }
                this.requestRefreshNewOrder(data)
            } else if (index == 2) {
                this.requestSuccessOrderList()
            }

            App.globalData.nickName = this.data.nickName
            this.processNotification()

        } else {
            Util.showServerErrorToast()
        }
    },

    onShow: function() {
        if (App.globalData.showNewOrder) {
            var data = {
                'openid': App.globalData.openid,
                'needUpdateAssets': true
            }
            this.requestRefreshNewOrder(data)
        }
        if (this.data.marqueeContent && intervalId != 0) {
            this.marqueeRun()
        }
    },

    onHide: function() {
        clearInterval(intervalId)
    },

    onUnload: function() {
        clearInterval(intervalId)
        intervalId = 0
    },

    onPullDownRefresh: function() {
        clearInterval(intervalId)
        this.requestMyData()
    },

    requestRefreshNewOrder: function(data) {
        var data = data
        Http.doGet('/my/new', data, this.processNewOrderList)
    },

    processNewOrderList: function(response) {
        if (response.code === App.RESP_OK) {
            var data = response.data
            if (App.globalData.showNewOrder) {
                this.setData({
                    currentTab: 1,
                    newOrderList: data.newOrderList,
                    hkRestDollar: data.hkRestDollar,
                    hkFrozenCapital: data.hkFrozenCapital,
                    usRestDollar: data.usRestDollar,
                    usFrozenCapital: data.usFrozenCapital,
                })
                App.globalData.showNewOrder = false
            } else {
                this.setData({
                    newOrderList: data.newOrderList
                })
            }
        } else {
            Util.showServerErrorToast()
        }
    },

    requestSuccessOrderList: function() {
        var data = {
            'openid': this.data.openid
        }
        Http.doGet('/my/success', data, this.processSuccessOrderList)
    },

    processSuccessOrderList: function(response) {
        if (response.code === App.RESP_OK) {
            this.setData({
                successOrderList: response.data
            })
        } else {
            Util.showServerErrorToast()
        }
    },

    onTabItemClickEvent: function(e) {
        var index = e.currentTarget.dataset.index
        this.setData({
            currentTab: index
        })

        if (index == 1) {
            var data = {
                'openid': this.data.openid,
                'needUpdateAssets': false
            }
            this.requestRefreshNewOrder(data)

        } else if (index == 2) {
            this.requestSuccessOrderList()
        }
    },

    onTapStockItem: function(event) {
        var stockId = event.currentTarget.dataset.stockId
        var stockScope = event.currentTarget.dataset.stockScope
        var isTrading = false
        if (stockScope == 'hk') {
            isTrading = this.data.hkTrading
        } else if (stockScope == 'us') {
            isTrading = this.data.usTrading
        }

        wx.navigateTo({
            url: '../exchange/exchange?stockId=' + stockId + '&trading=' + isTrading,
        })
    },

    processNotification: function() {
        var contentLength = this.data.marqueeContent.length * this.data.marqueeFontSize //文字长度
        var windowWidth = wx.getSystemInfoSync().windowWidth // 屏幕宽度
        this.setData({
            contentLength: contentLength,
            windowWidth: windowWidth,
            marqueeDistance: windowWidth + contentLength,
        })

        this.marqueeRun()
    },

    marqueeRun: function() {
        clearInterval(intervalId)
        intervalId = setInterval(() => {
            if (-this.data.marqueeDistance < this.data.contentLength) {
                this.setData({
                    marqueeDistance: this.data.marqueeDistance - this.data.marqueePace
                })
            } else {
                this.setData({
                    marqueeDistance: this.data.windowWidth + this.data.contentLength // 直接重新滚动
                })
                clearInterval(intervalId)
                this.marqueeRun()
            }
        }, this.data.interval)
    }
})