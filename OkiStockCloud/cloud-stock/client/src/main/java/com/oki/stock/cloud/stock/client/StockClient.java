package com.oki.stock.cloud.stock.client;

import com.oki.stock.cloud.base.common.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@Component
@FeignClient(name = "stock-server")
public interface StockClient {

    @GetMapping("/stock/hk")
    RespResult getHkStockList();

    @GetMapping("/stock/us")
    RespResult getUsStockList();

    @GetMapping(value = "/stock/detail")
    RespResult getStockDetail(@RequestParam("stockId") Integer stockId, @RequestParam("openid") String openid);

}
