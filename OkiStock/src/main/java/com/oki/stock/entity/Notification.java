package com.oki.stock.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {

    private Integer noId;

    private String title;

    private String message;

    //0 无效，1 有效
    private String enabled;

}
