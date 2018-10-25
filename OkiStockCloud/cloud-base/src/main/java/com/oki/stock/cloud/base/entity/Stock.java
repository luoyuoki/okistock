package com.oki.stock.cloud.base.entity;

import lombok.Data;

@Data
public class Stock {

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

    private String stockScope;

    private Integer roundNums;

    private Boolean rise;

}
