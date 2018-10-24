const App = getApp()
const Http = require('../../utils/http.js')
const Util = require('../../utils/util.js')

Page({

    quotePrice: '',
    quoteNums: '',

    data: {
        stockId: 0,
        stockName: '',
        currentPrice: '',
        changePrice: '',
        changePercent: '',
        highestPrice: '',
        lowestPrice: '',
        beginPrice: '',
        lastPrice: '',
        turnoverAsset: '',
        turnoverNums: '',
        peRatio: '',
        marketValue: '',
        turnoverRate: '',
        shockRange: '',
        roundNums: '',
        rise: false,
        stockScope: '',

        mostBuy: '-',
        mostSell: '-',

        orderPrice: '0',
        focusPrice: false,
        focusNums: false,

        isTrading: false
    },

    onLoad: function(options) {
        var stockId = options.stockId
        this.setData({
            isTrading: options.trading
        })

        var openid = App.globalData.openid
        var data = {
            'stockId': stockId,
            'openid': openid
        }
        Http.doGet('/stock/detail', data, this.processStockData)
    },

    processStockData: function(response) {
        if (response.code === App.RESP_OK) {
            var data = response.data
            this.setData({
                stockId: data.stock.stockId,
                stockName: data.stock.stockName,
                currentPrice: data.stock.currentPrice,
                changePrice: data.stock.changePrice,
                changePercent: data.stock.changePercent,
                highestPrice: data.stock.highestPrice,
                lowestPrice: data.stock.lowestPrice,
                beginPrice: data.stock.beginPrice,
                lastPrice: data.stock.lastPrice,
                turnoverAsset: data.stock.turnoverAsset,
                turnoverNums: data.stock.turnoverNums,
                peRatio: data.stock.peRatio,
                marketValue: data.stock.marketValue,
                turnoverRate: data.stock.turnoverRate,
                shockRange: data.stock.shockRange,
                roundNums: data.stock.roundNums,
                rise: data.stock.rise,
                stockScope: data.stock.stockScope,
                mostBuy: data.mostBuy,
                mostSell: data.mostSell
            })
        } else {
            Util.showServerErrorToast()
        }
    },

    showPricePrompt: function(tips, focus) {
        var that = this
        wx.showToast({
            title: tips,
            icon: 'none',
            duration: 2500,
            success: function() {
                setTimeout(function() {
                    that.setData({
                        focusPrice: true
                    })
                }, 2500)
            }
        })
    },

    showNumsPrompt: function(tips, focus) {
        var that = this
        wx.showToast({
            title: tips,
            icon: 'none',
            duration: 2500,
            success: function() {
                setTimeout(function() {
                    that.setData({
                        focusNums: true
                    })
                }, 2500)
            }
        })
    },

    orderSubmit: function(form) {
        if (!this.data.isTrading) {
            if (this.data.stockScope == 'hk') {
                Util.showToast('现在是港股非交易时段，不能下单', false)
            } else if (this.data.stockScope == 'us') {
                Util.showToast('现在是美股非交易时段，不能下单', false)
            }
            return
        }

        if (!this.quotePrice) {
            this.showPricePrompt('交易价格不可为空')
            return
        }
        if (!this.quoteNums) {
            this.showNumsPrompt('交易数量不可为空')
            return
        }

        if (!Util.isNumber(this.quotePrice) || !Util.isNumber(this.quoteNums)) {
            Util.showToast('你有点调皮', false)
            return
        }

        var roundNums = Number(this.data.roundNums)
        if (this.quoteNums % roundNums != 0) {
            this.showNumsPrompt('交易数量必须要为整手')
            return;
        }

        var formData = form.detail.value

        if (form.detail.target.id == 'buy') {
            if (this.quoteNums > this.data.mostBuy) {
                this.showNumsPrompt('超过了最多可买股数')
                return
            }
            formData.orderType = '0'
        } else if (form.detail.target.id == 'sell') {
            if (this.quoteNums > this.data.mostBuy) {
                this.showNumsPrompt('超过了最多可卖股数')
                return
            }
            formData.orderType = '1'
        }

        formData.openid = App.globalData.openid
        formData.stockId = this.data.stockId
        formData.stockName = this.data.stockName
        formData.stockScope = this.data.stockScope

        Http.doPost('/stock/order', formData, this.processOrderData)
    },

    processOrderData: function(response) {
        if (response.code === App.RESP_OK) {
            App.globalData.showNewOrder = true
            wx.showToast({
                title: '提交订单成功',
                icon: 'success',
                duration: 2500,
                success: function() {
                    setTimeout(function() {
                        wx.switchTab({
                            url: '../my/my',
                        })
                    }, 2500)
                }
            })
        } else {
            App.globalData.showNewOrder = false
            Util.showServerErrorToast()
        }
    },

    watchPrice: function(e) {
        this.quotePrice = e.detail.value
        if (this.quoteNums) {
            this.calcAmount(this.quotePrice, this.quoteNums)
        }
    },

    watchNums: function(e) {
        this.quoteNums = e.detail.value
        if (this.quotePrice) {
            this.calcAmount(this.quotePrice, this.quoteNums)
        }
    },

    calcAmount: function(price, nums) {
        if (price && nums) {
            var amount = '-'
            if (Util.isNumber(price) && Util.isNumber(nums)) {
                amount = (price * nums).toFixed(2)
            }
            this.setData({
                orderPrice: amount
            })
        }
    },

    onReady: function() {

    },

    onShow: function() {

    },

    onHide: function() {

    },

    onUnload: function() {

    },

    onPullDownRefresh: function() {

    }
})