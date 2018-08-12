package com.oki.stock.dto;

import java.io.Serializable;

public class NewOrderDto implements Serializable {

    private Integer orderId;

    private String stockName;

    private String quotePrice;

    private Integer quoteNums;

    private String orderType;

    private String orderStatus;

    private String commitTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }
}
