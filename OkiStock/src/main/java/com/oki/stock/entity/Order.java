package com.oki.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {

    private Integer orderId;

    private String openid;

    private Integer stockId;

    private String stockName;

    private String stockScope;

    private String quotePrice;

    private Integer quoteNums;

    // 0:买入 1:卖出
    private String orderType;

    // 0:等待成交   1:交易成功  2:交易失败
    private String orderStatus;

    private Date commitTime;

    private BigDecimal exchangePrice;

    private Date exchangeTime;

    public Order() {
    }

    public Order(Integer orderId, String openid, Integer stockId, String stockName, String stockScope, String quotePrice, Integer quoteNums, String orderType, String orderStatus, Date commitTime, BigDecimal exchangePrice, java.sql.Date exchangeTime) {
        this.orderId = orderId;
        this.openid = openid;
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockScope = stockScope;
        this.quotePrice = quotePrice;
        this.quoteNums = quoteNums;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.commitTime = commitTime;
        this.exchangePrice = exchangePrice;
        this.exchangeTime = exchangeTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockScope() {
        return stockScope;
    }

    public void setStockScope(String stockScope) {
        this.stockScope = stockScope;
    }

    public String getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(String quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Integer getQuoteNums() {
        return quoteNums;
    }

    public void setQuoteNums(Integer quoteNums) {
        this.quoteNums = quoteNums;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public BigDecimal getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(BigDecimal exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", openid='" + openid + '\'' +
                ", stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", stockScope='" + stockScope + '\'' +
                ", quotePrice='" + quotePrice + '\'' +
                ", quoteNums=" + quoteNums +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", commitTime=" + commitTime +
                ", exchangePrice=" + exchangePrice +
                ", exchangeTime=" + exchangeTime +
                '}';
    }
}
