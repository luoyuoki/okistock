package com.oki.stock.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProfitDTO implements Serializable {

    private String openid;

    private BigDecimal assets;

    private BigDecimal profitAmount;

    private String profitPercent;

}
