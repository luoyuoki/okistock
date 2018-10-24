package com.oki.stock.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
