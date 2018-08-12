package com.oki.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {

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

    private Date updateTime;

    public User() {
    }

    public User(Integer userId, String openid, String nickName, String avatarUrl, BigDecimal hkAssets, BigDecimal hkRestDollar, BigDecimal hkProfitAmount, String hkProfitPercent, BigDecimal hkFrozenCapital, BigDecimal usAssets, BigDecimal usRestDollar, BigDecimal usProfitAmount, String usProfitPercent, BigDecimal usFrozenCapital, Date updateTime) {
        this.userId = userId;
        this.openid = openid;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.hkAssets = hkAssets;
        this.hkRestDollar = hkRestDollar;
        this.hkProfitAmount = hkProfitAmount;
        this.hkProfitPercent = hkProfitPercent;
        this.hkFrozenCapital = hkFrozenCapital;
        this.usAssets = usAssets;
        this.usRestDollar = usRestDollar;
        this.usProfitAmount = usProfitAmount;
        this.usProfitPercent = usProfitPercent;
        this.usFrozenCapital = usFrozenCapital;
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public BigDecimal getHkAssets() {
        return hkAssets;
    }

    public void setHkAssets(BigDecimal hkAssets) {
        this.hkAssets = hkAssets;
    }

    public BigDecimal getHkRestDollar() {
        return hkRestDollar;
    }

    public void setHkRestDollar(BigDecimal hkRestDollar) {
        this.hkRestDollar = hkRestDollar;
    }

    public BigDecimal getHkProfitAmount() {
        return hkProfitAmount;
    }

    public void setHkProfitAmount(BigDecimal hkProfitAmount) {
        this.hkProfitAmount = hkProfitAmount;
    }

    public String getHkProfitPercent() {
        return hkProfitPercent;
    }

    public void setHkProfitPercent(String hkProfitPercent) {
        this.hkProfitPercent = hkProfitPercent;
    }

    public BigDecimal getUsAssets() {
        return usAssets;
    }

    public void setUsAssets(BigDecimal usAssets) {
        this.usAssets = usAssets;
    }

    public BigDecimal getUsRestDollar() {
        return usRestDollar;
    }

    public void setUsRestDollar(BigDecimal usRestDollar) {
        this.usRestDollar = usRestDollar;
    }

    public BigDecimal getUsProfitAmount() {
        return usProfitAmount;
    }

    public void setUsProfitAmount(BigDecimal usProfitAmount) {
        this.usProfitAmount = usProfitAmount;
    }

    public String getUsProfitPercent() {
        return usProfitPercent;
    }

    public void setUsProfitPercent(String usProfitPercent) {
        this.usProfitPercent = usProfitPercent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getHkFrozenCapital() {
        return hkFrozenCapital;
    }

    public void setHkFrozenCapital(BigDecimal hkFrozenCapital) {
        this.hkFrozenCapital = hkFrozenCapital;
    }

    public BigDecimal getUsFrozenCapital() {
        return usFrozenCapital;
    }

    public void setUsFrozenCapital(BigDecimal usFrozenCapital) {
        this.usFrozenCapital = usFrozenCapital;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", hkAssets=" + hkAssets +
                ", hkRestDollar=" + hkRestDollar +
                ", hkProfitAmount=" + hkProfitAmount +
                ", hkProfitPercent='" + hkProfitPercent + '\'' +
                ", hkFrozenCapital=" + hkFrozenCapital +
                ", usAssets=" + usAssets +
                ", usRestDollar=" + usRestDollar +
                ", usProfitAmount=" + usProfitAmount +
                ", usProfitPercent='" + usProfitPercent + '\'' +
                ", usFrozenCapital=" + usFrozenCapital +
                ", updateTime=" + updateTime +
                '}';
    }
}
