package com.oki.stock.cloud.base.exception;


import com.oki.stock.cloud.base.common.CodeMsg;
import lombok.Getter;

@Getter
public class StockServerException extends RuntimeException {

    private CodeMsg codeMsg;

    public StockServerException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
