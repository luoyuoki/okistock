package com.oki.stock.controller;

import com.oki.stock.common.CodeMsg;
import com.oki.stock.common.RespResult;
import com.oki.stock.common.TradingFlag;
import com.oki.stock.dto.*;
import com.oki.stock.entity.*;
import com.oki.stock.service.*;
import com.oki.stock.util.SpringContextUtil;
import com.oki.stock.vo.MyMainInfoVO;
import com.oki.stock.vo.NewOrderVO;
import com.oki.stock.vo.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my")
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HolderService holderService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/main")
    public RespResult getMyMainInfo(String openid) {
        UserDTO user = userService.getUserInfoByOpenid(openid);
        if (user != null) {
            MyMainInfoVO infoVO = new MyMainInfoVO();
            infoVO.setUser(user);

            List<HolderDTO> holderList = holderService.getUserHoldersByOpenid(openid);
            infoVO.setHolderList(holderList);

            Notification notification = notificationService.getLastNotification();
            if (notification != null) {
                NotificationVO notificationVO = new NotificationVO();
                notificationVO.setTitle(notification.getTitle());
                notificationVO.setMessage(notification.getMessage());
            }
            infoVO.setNotification(notification);

            infoVO.setTradingFlag(SpringContextUtil.getBean(TradingFlag.class));
            return RespResult.bySuccess(infoVO);
        } else {
            return RespResult.byError(CodeMsg.USER_NOT_EXIST);
        }
    }

    @GetMapping("/new")
    public RespResult getNewOrderList(String openid, Boolean needUpdateAssets) {
        List<NewOrderDTO> orderList = orderService.getNewOrdersByUser(openid);
        if (orderList != null) {
            NewOrderVO orderVO = new NewOrderVO();
            orderVO.setNewOrderList(orderList);

            if (needUpdateAssets) {
                UserDTO ud = userService.getUserInfoByOpenid(openid);
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
    public RespResult getSuccessOrderList(String openid) {
        List<SuccessOrderDTO> orderList = orderService.getSuccessOrdersByUser(openid);
        if (orderList != null) {
            return RespResult.bySuccess(orderList);
        } else {
            return RespResult.byError();
        }
    }

}
