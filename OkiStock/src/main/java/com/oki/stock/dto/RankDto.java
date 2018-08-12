package com.oki.stock.dto;

import java.io.Serializable;

public class RankDto implements Serializable {

    private String nickName;

    private String avatarUrl;

    private Boolean isRise;

    public Boolean getRise() {
        return isRise;
    }

    public void setRise(Boolean rise) {
        isRise = rise;
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


}
