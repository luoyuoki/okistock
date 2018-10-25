package com.oki.stock.cloud.base.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDTO implements Serializable {

    private Integer stockId;

    private String stockName;

    private String currentPrice;

    private String changePercent;

    private Boolean rise;

}
