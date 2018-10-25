package com.oki.stock.cloud.base.common;

import lombok.Getter;

@Getter
public enum OrderType {

    BUY("0"),

    SELL("1");

    private String type;

    OrderType(String type) {
        this.type = type;
    }
}
