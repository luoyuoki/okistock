package com.oki.stock.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

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

    private Boolean isHkRise;

    private Boolean isUsRise;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHkAssets() {
        return hkAssets;
    }

    public void setHkAssets(String hkAssets) {
        this.hkAssets = hkAssets;
    }

    public String getHkRestDollar() {
        return hkRestDollar;
    }

    public void setHkRestDollar(String hkRestDollar) {
        this.hkRestDollar = hkRestDollar;
    }

    public String getHkProfitAmount() {
        return hkProfitAmount;
    }

    public void setHkProfitAmount(String hkProfitAmount) {
        this.hkProfitAmount = hkProfitAmount;
    }

    public String getHkProfitPercent() {
        return hkProfitPercent;
    }

    public void setHkProfitPercent(String hkProfitPercent) {
        this.hkProfitPercent = hkProfitPercent;
    }

    public String getUsAssets() {
        return usAssets;
    }

    public void setUsAssets(String usAssets) {
        this.usAssets = usAssets;
    }

    public String getUsRestDollar() {
        return usRestDollar;
    }

    public void setUsRestDollar(String usRestDollar) {
        this.usRestDollar = usRestDollar;
    }

    public String getUsProfitAmount() {
        return usProfitAmount;
    }

    public void setUsProfitAmount(String usProfitAmount) {
        this.usProfitAmount = usProfitAmount;
    }

    public String getUsProfitPercent() {
        return usProfitPercent;
    }

    public void setUsProfitPercent(String usProfitPercent) {
        this.usProfitPercent = usProfitPercent;
    }

    public Boolean getHkRise() {
        return isHkRise;
    }

    public void setHkRise(Boolean hkRise) {
        isHkRise = hkRise;
    }

    public Boolean getUsRise() {
        return isUsRise;
    }

    public void setUsRise(Boolean usRise) {
        isUsRise = usRise;
    }

    public String getHkFrozenCapital() {
        return hkFrozenCapital;
    }

    public void setHkFrozenCapital(String hkFrozenCapital) {
        this.hkFrozenCapital = hkFrozenCapital;
    }

    public String getUsFrozenCapital() {
        return usFrozenCapital;
    }

    public void setUsFrozenCapital(String usFrozenCapital) {
        this.usFrozenCapital = usFrozenCapital;
    }
}
