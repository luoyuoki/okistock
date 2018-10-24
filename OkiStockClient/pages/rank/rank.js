const App = getApp()
const Http = require('../../utils/http.js')
const Util = require('../../utils/util.js')

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
            myName: App.globalData.nickName
        })
    },

    onReady: function() {
        this.requestHkRankList()
    },

    onShow: function() {

    },

    requestHkRankList: function() {
        var data = {}
        Http.doGet('/rank/hk', data, this.processHkRankList)
    },

    requestUsRankList: function() {
        var data = {}
        Http.doGet('/rank/us', data, this.processUsRankList)
    },

    processHkRankList: function(response) {
        if (response.code === App.RESP_OK) {
            var hkRankList = response.data
            for (var item in hkRankList) {
                var rank = hkRankList[item]
                var name = rank.nickName
                rank.isMine = this.data.myName == name ? true : false
                rank.nickName = Util.filterSensitive(name)
            }

            this.setData({
                hkRankList: hkRankList
            })
        } else {
            Util.showServerErrorToast()
        }
    },

    processUsRankList: function (response) {
        if (response.code === App.RESP_OK) {
            var usRankList = response.data
            for (var item in usRankList) {
                var rank = usRankList[item]
                var name = rank.nickName
                rank.isMine = this.data.myName == name ? true : false
                rank.nickName = Util.filterSensitive(name)
            }

            this.setData({
                usRankList: usRankList
            })

            this.isClicked = true
        } else {
            Util.showServerErrorToast()
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
        Util.showToast('让我们恭喜这些上榜的朋友', false)
    }

})