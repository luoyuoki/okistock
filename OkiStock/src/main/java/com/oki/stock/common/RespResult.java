package com.oki.stock.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespResult<T> implements Serializable {

    private static final long serialVersionUID = 8605070050365561506L;

    private Integer code;

    private String msg;

    private T data;

    private RespResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private RespResult(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getDesc();
        }
    }

    public static <T> RespResult<T> bySuccess(T data) {
        return new RespResult<>(CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.getDesc(), data);
    }

    public static <T> RespResult<T> bySuccess() {
        return new RespResult(CodeMsg.SUCCESS);
    }

    public static RespResult byError() {
        return new RespResult(CodeMsg.SERVER_ERROR);
    }

    public static RespResult byError(CodeMsg codeMsg) {
        return new RespResult(codeMsg);
    }

}
