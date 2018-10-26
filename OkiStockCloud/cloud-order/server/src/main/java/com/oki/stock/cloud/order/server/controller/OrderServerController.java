package com.oki.stock.cloud.order.server.controller;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.base.dto.HolderDTO;
import com.oki.stock.cloud.base.dto.NewOrderDTO;
import com.oki.stock.cloud.base.dto.SuccessOrderDTO;
import com.oki.stock.cloud.base.dto.UserDTO;
import com.oki.stock.cloud.base.vo.NewOrderVO;
import com.oki.stock.cloud.order.server.service.OrderService;
import com.oki.stock.cloud.user.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/26
 */
@RestController
@RequestMapping("/order")
public class OrderServerController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserClient userClient;

    @PostMapping("/hold")
    public List<HolderDTO> getHolderList(@RequestParam("openid") String openid) {
        return orderService.getUserHoldersByOpenid(openid);
    }

    @GetMapping("/new")
    public RespResult getNewOrderList(@RequestParam("openid") String openid, @RequestParam("needUpdateAssets") Boolean needUpdateAssets) {
        List<NewOrderDTO> orderList = orderService.getNewOrdersByUser(openid);
        if (orderList != null) {
            NewOrderVO orderVO = new NewOrderVO();
            orderVO.setNewOrderList(orderList);

            if (needUpdateAssets) {
                UserDTO ud = userClient.getUserInfoByOpenid(openid);
                orderVO.setHkRestDollar(ud.getHkRestDollar());
                orderVO.setHkFrozenCapital(ud.getHkFrozenCapital());
                orderVO.setUsRestDollar(ud.getUsRestDollar());
                orderVO.setUsFrozenCapital(ud.getUsFrozenCapital());
            }
            return RespResult.bySuccess(orderVO);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping("/success")
    public RespResult getSuccessOrderList(@RequestParam("openid") String openid) {
        List<SuccessOrderDTO> orderList = orderService.getSuccessOrdersByUser(openid);
        if (orderList != null) {
            return RespResult.bySuccess(orderList);
        } else {
            return RespResult.byError();
        }
    }
}
