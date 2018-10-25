package com.oki.stock.cloud.base.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RankDTO implements Serializable {

    private String nickName;

    private String avatarUrl;

    private Boolean rise;

}
