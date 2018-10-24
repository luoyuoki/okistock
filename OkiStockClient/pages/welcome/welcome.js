const App = getApp()
const Http = require('../../utils/http.js')
const Util = require('../../utils/util.js')

Page({

    data: {
        canIUse: wx.canIUse('button.open-type.getUserInfo')
    },

    onLoad: function (options) {
        var openid = wx.getStorageSync('openid')
        if (openid) {
            wx.switchTab({
                url: '../my/my',
            })
        } else {
            wx.getSetting({
                success: function (res) {
                    if (res.authSetting['scope.userInfo']) {
                        wx.getUserInfo({
                            success: function (res) {
                                console.log(res.userInfo)
                            }
                        })
                    }
                }
            })
        }
    },

    doStart: function (e) {
        if (e.detail.userInfo == null) {
            wx.showModal({
                title: '提示',
                content: '需要你的授权才可正常使用',
                showCancel: false
            })
            return
        }
        wx.login({
            success: res => {
                var code = res.code
                if (code) {
                    var data = { 'code': code, 'nickName': e.detail.userInfo.nickName, 'avatarUrl': e.detail.userInfo.avatarUrl }
                    Http.doGet('/login', data, this.processLoginData)
                }

            }
        })

    },

    processLoginData: response => {
        if (response.code === App.RESP_OK) {
            var openid = response.data.openid
            App.globalData.openid = openid
            wx.switchTab({
                url: '../my/my',
            })
            wx.setStorageSync('openid', openid)
        } else {
            Util.showServerErrorToast()
        }
    },


    onReady: function () {

    },


    onShow: function () {

    },


    onHide: function () {

    },

    onUnload: function () {

    },

})