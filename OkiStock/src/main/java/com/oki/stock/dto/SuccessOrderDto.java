package com.oki.stock.dto;

import java.io.Serializable;

public class SuccessOrderDto implements Serializable {

    private Integer orderId;

    private String stockName;

    private String orderType;

    private String exchangePrice;

    private Integer quoteNums;

    private String exchangeTime;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(String exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public Integer getQuoteNums() {
        return quoteNums;
    }

    public void setQuoteNums(Integer quoteNums) {
        this.quoteNums = quoteNums;
    }

    public String getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(String exchangeTime) {
        this.exchangeTime = exchangeTime;
    }
}
