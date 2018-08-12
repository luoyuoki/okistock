const app = getApp()
const http = require('../../utils/http.js')
const util = require('../../utils/util.js')

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
        http.doGet('/about/main', data, this.processQuestionList)
    },

    processQuestionList: function(data) {
        if (data.success) {
            this.setData({
                questionList: data.questionList
            })
        } else {
            util.showServerErrorToast()
        }
    },

    bindFormSubmit: function(form) {
        this.myForm = form
        http.doPost('/about/advice', app.globalData.nickName + ":" + util.formatTime(new Date()) + ":" + form.detail.value.advice, this.processAdviceData)
    },

    processAdviceData: function(data) {
        if (data.success) {
            util.showToast('发射成功', true)
            this.setData({
                content: ''
            })
        } else {
            util.showServerErrorToast()
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
            util.showToast('就先到这吧，没事来逛逛')
            this.setData({
                isShowTips: true
            })
        }
    }

})