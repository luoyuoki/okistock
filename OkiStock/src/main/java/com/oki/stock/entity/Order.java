package com.oki.stock.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable{

    private static final long serialVersionUID = 8622493900016375187L;

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

}
