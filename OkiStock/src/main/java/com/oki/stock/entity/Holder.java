package com.oki.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Holder implements Serializable {

    private Integer holderId;

    private String openid;

    private Integer stockId;

    private String stockName;

    private String stockScope;

    private BigDecimal costPrice;

    private Integer stockNums;

    private BigDecimal profitAmount;

    private String profitPercent;

    private Date updateTime;

    public Holder() {
    }

    public Holder(Integer holderId, String openid, Integer stockId, String stockName, String stockScope, BigDecimal costPrice, Integer stockNums, BigDecimal profitAmount, String profitPercent, Date updateTime) {
        this.holderId = holderId;
        this.openid = openid;
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockScope = stockScope;
        this.costPrice = costPrice;
        this.stockNums = stockNums;
        this.profitAmount = profitAmount;
        this.profitPercent = profitPercent;
        this.updateTime = updateTime;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
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

    public String getStockScope() {
        return stockScope;
    }

    public void setStockScope(String stockScope) {
        this.stockScope = stockScope;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getStockNums() {
        return stockNums;
    }

    public void setStockNums(Integer stockNums) {
        this.stockNums = stockNums;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(String profitPercent) {
        this.profitPercent = profitPercent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "holderId=" + holderId +
                ", openid='" + openid + '\'' +
                ", stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", stockScope='" + stockScope + '\'' +
                ", costPrice=" + costPrice +
                ", stockNums=" + stockNums +
                ", profitAmount=" + profitAmount +
                ", profitPercent='" + profitPercent + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
