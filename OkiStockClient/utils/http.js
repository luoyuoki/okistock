const app = getApp()
const util = require('util.js')

function doGet(path, data, callBack) {
    wx.showNavigationBarLoading()
    wx.request({
        url: app.globalData.baseUrl + path,
        method: 'GET',
        data: data,
        success: res => {
            if (res.data) {
                callBack(res.data);
            } else {
                util.showToast('大概是出了不可描述的问题', false)
            }
        },
        fail: error => {
            wx.redirectTo({
                url: '/pages/error/error',
            })
        },
        complete: function() {
            wx.hideNavigationBarLoading()
        }
    })
}

function doPost(path, data, callBack) {
    wx.showNavigationBarLoading()
    wx.request({
        url: app.globalData.baseUrl + path,
        method: 'POST',
        data: JSON.stringify(data),
        header: {
            'Content-Type': 'application/json'
        },
        success: res => {
            if (res.data) {
                callBack(res.data);
            } else {
                util.showToast('大概是出了不可描述的问题', false)
            }
        },
        fail: error => {
            wx.redirectTo({
                url: '/pages/error/error',
            })
        },
        complete: function() {
            wx.hideNavigationBarLoading()
        }
    })
}

module.exports = {
    doGet: doGet,
    doPost: doPost
}