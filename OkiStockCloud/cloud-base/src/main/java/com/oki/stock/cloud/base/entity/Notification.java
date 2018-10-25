package com.oki.stock.cloud.base.entity;

import lombok.Data;

@Data
public class Notification {

    private Integer noId;

    private String title;

    private String message;

    //0 无效，1 有效
    private String enabled;

}
