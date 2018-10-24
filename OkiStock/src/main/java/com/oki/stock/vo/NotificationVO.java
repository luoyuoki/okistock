package com.oki.stock.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationVO implements Serializable {

    private static final long serialVersionUID = 3999730301382887391L;

    private String title;

    private String message;
}
