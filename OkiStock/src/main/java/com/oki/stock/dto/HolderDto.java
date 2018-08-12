package com.oki.stock.dto;

import java.io.Serializable;

public class HolderDto implements Serializable {

    private Integer stockId;

    private String stockName;

    private String stockScope;

    private String costPrice;

    private Integer stockNums;

    private String profitAmount;

    private String profitPercent;

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

    public String getStockScope() {
        return stockScope;
    }

    public void setStockScope(String stockScope) {
        this.stockScope = stockScope;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getStockNums() {
        return stockNums;
    }

    public void setStockNums(Integer stockNums) {
        this.stockNums = stockNums;
    }

    public String getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(String profitPercent) {
        this.profitPercent = profitPercent;
    }

    public Boolean getRise() {
        return isRise;
    }

    public void setRise(Boolean rise) {
        isRise = rise;
    }
}
