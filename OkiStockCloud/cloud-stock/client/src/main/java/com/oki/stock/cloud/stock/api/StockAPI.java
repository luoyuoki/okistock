package com.oki.stock.cloud.stock.api;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.stock.client.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@RestController
@RequestMapping("/stock")
public class StockAPI {

    @Autowired
    private StockClient stockClient;

    @GetMapping("/hk")
    public RespResult getHkStockList() {
        return stockClient.getHkStockList();
    }

    @GetMapping("/us")
    public RespResult getUsStockList() {
        return stockClient.getUsStockList();
    }

    @GetMapping("/detail")
    public RespResult getStockDetail(@RequestParam("stockId") Integer stockId, @RequestParam("openid") String openid) {
        return stockClient.getStockDetail(stockId, openid);
    }

}
