const app = getApp()
const http = require('../../utils/http.js')
const util = require('../../utils/util.js')

Page({

    isClicked: false,

    data: {
        tabArr: ['港股收益排行', '美股收益排行'],
        currentTab: 0,
        hkRankList: [],
        usRankList: [],
        myName: ''
    },

    onLoad: function(options) {
        this.setData({
            myName: app.globalData.nickName
        })
    },

    onReady: function() {
        this.requestHkRankList()
    },

    onShow: function() {

    },

    requestHkRankList: function() {
        var data = {}
        http.doGet('/rank/hk', data, this.processHkRankList)
    },

    requestUsRankList: function() {
        var data = {}
        http.doGet('/rank/us', data, this.processUsRankList)
    },

    processHkRankList: function(data) {
        if (data.success) {
            var hkRankList = data.hkRankList
            for (var item in hkRankList) {
                var rank = hkRankList[item]
                var name = rank.nickName
                rank.isMine = this.data.myName == name ? true : false
                rank.nickName = util.filterSensitive(name)
            }

            this.setData({
                hkRankList: hkRankList
            })
        } else {
            util.showServerErrorToast()
        }
    },

    processUsRankList: function(data) {
        if (data.success) {
            var usRankList = data.usRankList
            for (var item in usRankList) {
                var rank = usRankList[item]
                var name = rank.nickName
                rank.isMine = this.data.myName == name ? true : false
                rank.nickName = util.filterSensitive(name)
            }

            this.setData({
                usRankList: data.usRankList
            })

            this.isClicked = true
        } else {
            util.showServerErrorToast()
        }
    },

    onTabItemClickEvent: function(e) {
        var index = e.currentTarget.dataset.index;
        this.setData({
            currentTab: index
        })

        if (index == 1 && !this.isClicked) {
            this.requestUsRankList()
        }
    },

    onHide: function() {

    },

    onUnload: function() {
        this.isClicked = false
    },

    onPullDownRefresh: function() {
        if (this.data.currentTab == 0) {
            this.requestHkRankList()
        } else if (this.data.currentTab == 1) {
            this.requestUsRankList()
        }
    },

    onReachBottom: function() {
        util.showToast('让我们恭喜这些上榜的朋友', false)
    }

})