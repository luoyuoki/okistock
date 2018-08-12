package com.oki.stock.dto;

import java.io.Serializable;

public class StockDto implements Serializable {

    private Integer stockId;

    private String stockName;

    private String currentPrice;

    private String changePercent;

    private Boolean isRise;

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

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public Boolean getRise() {
        return isRise;
    }

    public void setRise(Boolean rise) {
        isRise = rise;
    }
}
