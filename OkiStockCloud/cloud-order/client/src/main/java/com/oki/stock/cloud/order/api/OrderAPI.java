package com.oki.stock.cloud.order.api;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.order.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 0ki
 * @date 2018/10/26
 */
@RestController
@RequestMapping("/order")
public class OrderAPI {

    @Autowired
    private OrderClient orderClient;

    @GetMapping("/new")
    public RespResult getNewOrderList(String openid, Boolean needUpdateAssets) {
        return orderClient.getNewOrderList(openid, needUpdateAssets);
    }

    @GetMapping("/success")
    public RespResult getSuccessOrderList(String openid) {
        return orderClient.getSuccessOrderList(openid);
    }

}
