package com.oki.stock.cloud.base.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SuccessOrderDTO implements Serializable {

    private Integer orderId;

    private String stockName;

    private String orderType;

    private String exchangePrice;

    private Integer quoteNums;

    private String exchangeTime;

}
