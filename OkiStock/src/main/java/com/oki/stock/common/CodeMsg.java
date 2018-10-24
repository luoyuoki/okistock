package com.oki.stock.common;

import lombok.Getter;

@Getter
public enum CodeMsg {

    SUCCESS(0, "Success"),

    SERVER_ERROR(1, "Server Error"),

    USER_NOT_EXIST(10,"用户不存在"),

    ADD_STOCK_HOLDER_ERROR(11, "增加持仓股票失败"),

    MODIFY_STOCK_HOLDER_ERROR(12, "更新持仓股票失败"),

    DROP_STOCK_HOLDER_ERROR(13, "删除持仓股票失败"),

    ADD_USER_ERROR(14, "添加新用户失败"),

    ADD_USER_INFO_EMPTY(15, "添加新用户关键信息为空"),

    MODIFY_USER_ASSETS_ERROR(16, "更新用户资产失败"),

    ADD_ORDER_INFO_EMPTY(17, "下单信息为空"),

    ADD_ORDER_ERROR(18, "下单失败"),

    MODIFY_ORDER_ERROR(19, "更新订单失败"),

    MODIFY_EXPIRED_ORDER_ERROR(20, "更新过期订单失败"),

    DROP_EXPIRED_ORDER_ERROR(21, "删除过期订单失败");

    private Integer code;

    private String desc;

    CodeMsg(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
