package com.oki.stock.cloud.base.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HolderDTO implements Serializable {

    private Integer stockId;

    private String stockName;

    private String stockScope;

    private String costPrice;

    private Integer stockNums;

    private String profitAmount;

    private String profitPercent;

    private Boolean rise;

}
