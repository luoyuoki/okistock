const formatTime = date => {
    const month = date.getMonth() + 1
    const day = date.getDate()

    return [month, day].map(formatNumber).join('/')
}

const formatNumber = n => {
    n = n.toString()
    return n[1] ? n : '0' + n
}

const isNumber = input => {　　
    var re = /^[0-9]+.?[0-9]*[0-9]$/;
    return re.test(input) ? true : false
}

const randomNum = (lower, upper) => {
    return Math.floor(Math.random() * (upper - lower)) + lower
}

const filterSensitive = name => {
    var nameLength = name.length
    if (name.length > 1) {
        var randomPosition = randomNum(nameLength / 2, nameLength)
        var newName = name.substring(0, randomPosition)
        for (var i = 0; i < nameLength - randomPosition; i++) {
            newName += '*'
        }
        return newName
    } else {
        return '*'
    }
}

const showToast = (text, withIcon) => {
    wx.showToast({
        title: text,
        duration: 3000,
        icon: withIcon == true ? 'success' : 'none'
    })
}

const showServerErrorToast = () => {
    wx.showToast({
        title: '服务端报错了，尴尬',
        duration: 3000,
        icon: 'none'
    })
}

module.exports = {
    formatTime: formatTime,
    isNumber: isNumber,
    filterSensitive: filterSensitive,
    showToast: showToast,
    showServerErrorToast: showServerErrorToast
}