package com.oki.stock.common;

import lombok.Getter;

@Getter
public enum OrderStatus {

    TRADE_WAIT("0"),

    TRADE_SUCCESS("1"),

    TRADE_EXPIRED("2");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

}
