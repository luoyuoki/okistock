package com.oki.stock.cloud.base.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {

    private Integer userId;

    private String openid;

    private String nickName;

    private String avatarUrl;

    private BigDecimal hkAssets;

    private BigDecimal hkRestDollar;

    private BigDecimal hkProfitAmount;

    private String hkProfitPercent;

    private BigDecimal hkFrozenCapital;

    private BigDecimal usAssets;

    private BigDecimal usRestDollar;

    private BigDecimal usProfitAmount;

    private String usProfitPercent;

    private BigDecimal usFrozenCapital;

}
