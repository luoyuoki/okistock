package com.oki.stock.cloud.base.exception;

import com.oki.stock.cloud.base.common.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class StockServerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public RespResult handleServerException(HttpServletRequest request, Exception e) {
        log.error("{} occur exception",request.getRequestURI(),e);
        if (e instanceof StockServerException) {
            StockServerException stockServerException = (StockServerException) e;
            return RespResult.byError(stockServerException.getCodeMsg());
        } else {
            return RespResult.byError();
        }
    }
}
