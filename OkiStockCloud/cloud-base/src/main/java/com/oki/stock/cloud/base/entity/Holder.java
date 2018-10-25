package com.oki.stock.cloud.base.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Holder {

    private Integer holderId;

    private String openid;

    private Integer stockId;

    private String stockName;

    private String stockScope;

    private BigDecimal costPrice;

    private Integer stockNums;

    private BigDecimal profitAmount;

    private String profitPercent;

}
