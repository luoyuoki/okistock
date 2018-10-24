package com.oki.stock.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewOrderDTO implements Serializable {

    private Integer orderId;

    private String stockName;

    private String quotePrice;

    private Integer quoteNums;

    private String orderType;

    private String orderStatus;

    private String commitTime;

}
