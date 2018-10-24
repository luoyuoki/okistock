const App = getApp()
const Http = require('../../utils/http.js')
const Util = require('../../utils/util.js')

Page({

    data: {
        questionList: [],
        content: '',
        isReachedBottom: false,
        isShowTips: false
    },

    onLoad: function(options) {

    },

    onReady: function() {
        this.requestQuestionList()
    },

    onShow: function() {

    },

    requestQuestionList: function() {
        var data = {}
        Http.doGet('/about/main', data, this.processQuestionList)
    },

    processQuestionList: function(response) {
        if (response.code === App.RESP_OK) {
            this.setData({
                questionList: response.data
            })
        } else {
            Util.showServerErrorToast()
        }
    },

    bindFormSubmit: function(form) {
        this.myForm = form
        Http.doPost('/about/advice', App.globalData.nickName + ":" + Util.formatTime(new Date()) + ":" + form.detail.value.advice, this.processAdviceData)
    },

    processAdviceData: function(response) {
        if (response.code === App.RESP_OK) {
            Util.showToast('发射成功', true)
            this.setData({
                content: ''
            })
        } else {
            Util.showServerErrorToast()
        }
    },

    onHide: function() {

    },

    onUnload: function() {

    },

    onReachBottom: function() {
        if (!this.data.isReachedBottom) {
            this.setData({
                isReachedBottom: true
            })
            return
        }
        if (this.data.isReachedBottom && !this.data.isShowTips) {
            Util.showToast('就先到这吧，没事来逛逛')
            this.setData({
                isShowTips: true
            })
        }
    }

})