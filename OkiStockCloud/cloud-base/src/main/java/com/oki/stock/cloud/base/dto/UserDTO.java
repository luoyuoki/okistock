package com.oki.stock.cloud.base.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String openid;

    private String nickName;

    private String avatarUrl;

    private String hkAssets;

    private String hkRestDollar;

    private String hkProfitAmount;

    private String hkProfitPercent;

    private String hkFrozenCapital;

    private String usAssets;

    private String usRestDollar;

    private String usProfitAmount;

    private String usProfitPercent;

    private String usFrozenCapital;

    private Boolean hkRise;

    private Boolean usRise;

}
