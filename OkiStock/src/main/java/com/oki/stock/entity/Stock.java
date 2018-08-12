package com.oki.stock.entity;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {

    private Integer stockId;

    private String stockName;

    private String currentPrice;

    private String changePrice;

    private String changePercent;

    private String highestPrice;

    private String lowestPrice;

    private String beginPrice;

    private String lastPrice;

    private String turnoverAsset;

    private String turnoverNums;

    private String peRatio;

    private String marketValue;

    private String turnoverRate;

    private String shockRange;

    private Date updateTime;

    private String stockScope;

    private Integer roundNums;

    private Boolean isRise;

    public Stock() {
    }

    public Stock(Integer stockId, String stockName, String currentPrice, String changePrice, String changePercent, String highestPrice, String lowestPrice, String beginPrice, String lastPrice, String turnoverAsset, String turnoverNums, String peRatio, String marketValue, String turnoverRate, String shockRange, Date updateTime,String stockScope,Integer roundNums) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.changePrice = changePrice;
        this.changePercent = changePercent;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.beginPrice = beginPrice;
        this.lastPrice = lastPrice;
        this.turnoverAsset = turnoverAsset;
        this.turnoverNums = turnoverNums;
        this.peRatio = peRatio;
        this.marketValue = marketValue;
        this.turnoverRate = turnoverRate;
        this.shockRange = shockRange;
        this.updateTime = updateTime;
        this.stockScope = stockScope;
        this.roundNums = roundNums;
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

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(String beginPrice) {
        this.beginPrice = beginPrice;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getTurnoverAsset() {
        return turnoverAsset;
    }

    public void setTurnoverAsset(String turnoverAsset) {
        this.turnoverAsset = turnoverAsset;
    }

    public String getTurnoverNums() {
        return turnoverNums;
    }

    public void setTurnoverNums(String turnoverNums) {
        this.turnoverNums = turnoverNums;
    }

    public String getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(String peRatio) {
        this.peRatio = peRatio;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public String getShockRange() {
        return shockRange;
    }

    public void setShockRange(String shockRange) {
        this.shockRange = shockRange;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStockScope() {
        return stockScope;
    }

    public void setStockScope(String stockScope) {
        this.stockScope = stockScope;
    }

    public Integer getRoundNums() {
        return roundNums;
    }

    public void setRoundNums(Integer roundNums) {
        this.roundNums = roundNums;
    }

    public Boolean getRise() {
        return isRise;
    }

    public void setRise(Boolean rise) {
        isRise = rise;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", changePrice='" + changePrice + '\'' +
                ", changePercent='" + changePercent + '\'' +
                ", highestPrice='" + highestPrice + '\'' +
                ", lowestPrice='" + lowestPrice + '\'' +
                ", beginPrice='" + beginPrice + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", turnoverAsset='" + turnoverAsset + '\'' +
                ", turnoverNums='" + turnoverNums + '\'' +
                ", peRatio='" + peRatio + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", turnoverRate='" + turnoverRate + '\'' +
                ", shockRange='" + shockRange + '\'' +
                ", updateTime=" + updateTime +
                ", stockScope='" + stockScope + '\'' +
                ", roundNums=" + roundNums +
                ", isRise=" + isRise +
                '}';
    }
}
