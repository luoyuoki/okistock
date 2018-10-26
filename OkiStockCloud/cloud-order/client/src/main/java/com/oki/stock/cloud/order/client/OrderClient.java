package com.oki.stock.cloud.order.client;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.base.dto.HolderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/26
 */
@Component
@FeignClient(name = "order-server")
public interface OrderClient {

    @GetMapping("/order/new")
    RespResult getNewOrderList(@RequestParam("openid") String openid, @RequestParam("needUpdateAssets") Boolean needUpdateAssets);

    @GetMapping("/order/success")
    RespResult getSuccessOrderList(@RequestParam("openid") String openid);

    @PostMapping("/order/hold")
    List<HolderDTO> getHolderList(@RequestParam("openid") String openid);

}
