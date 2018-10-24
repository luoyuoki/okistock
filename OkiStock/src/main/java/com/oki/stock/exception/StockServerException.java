package com.oki.stock.exception;


import com.oki.stock.common.CodeMsg;
import lombok.Getter;

@Getter
public class StockServerException extends RuntimeException {

    private CodeMsg codeMsg;

    public StockServerException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
