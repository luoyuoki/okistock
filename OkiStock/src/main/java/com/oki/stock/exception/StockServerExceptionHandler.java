package com.oki.stock.exception;

import com.oki.stock.common.RespResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class StockServerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public RespResult handleServerException(HttpServletRequest request, Exception e) {
        if (e instanceof StockServerException) {
            StockServerException stockServerException = (StockServerException) e;
            return RespResult.byError(stockServerException.getCodeMsg());
        } else {
            return RespResult.byError();
        }
    }
}
