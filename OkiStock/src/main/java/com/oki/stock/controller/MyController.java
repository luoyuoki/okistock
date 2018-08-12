package com.oki.stock.controller;

import com.oki.stock.dto.HolderDto;
import com.oki.stock.dto.NewOrderDto;
import com.oki.stock.dto.SuccessOrderDto;
import com.oki.stock.dto.UserDto;
import com.oki.stock.entity.*;
import com.oki.stock.service.*;
import com.oki.stock.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> getMyMainInfo(String openid) {
        Map<String, Object> modelMap = new HashMap<>();
        UserDto user = userService.getUserInfoByOpenid(openid);
        if (user != null) {
            modelMap.put("success", true);
            modelMap.put("user", user);

        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/hold")
    public Map<String, Object> getHolderList(String openid) {
        Map<String, Object> modelMap = new HashMap<>();
        List<HolderDto> holderList = holderService.getUserHoldersByOpenid(openid);
        if (holderList != null) {
            modelMap.put("success", true);
            modelMap.put("stockHolderList", holderList);

            TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);
            modelMap.put("hkTrading", tradingFlag.getHkTrading());
            modelMap.put("usTrading", tradingFlag.getUsTrading());
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/new")
    public Map<String, Object> getNewOrderList(String openid, Boolean needUpdateAssets) {
        Map<String, Object> modelMap = new HashMap<>();
        List<NewOrderDto> orderList = orderService.getNewOrdersByUser(openid);
        if (orderList != null) {
            modelMap.put("success", true);
            modelMap.put("newOrderList", orderList);

            if (needUpdateAssets) {
                UserDto ud = userService.getUserInfoByOpenid(openid);
                modelMap.put("hkRestDollar", ud.getHkRestDollar());
                modelMap.put("hkFrozenCapital", ud.getHkFrozenCapital());
                modelMap.put("usRestDollar", ud.getUsRestDollar());
                modelMap.put("usFrozenCapital", ud.getUsFrozenCapital());
            }
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/success")
    public Map<String, Object> getSuccessOrderList(String openid) {
        Map<String, Object> modelMap = new HashMap<>();
        List<SuccessOrderDto> orderList = orderService.getSuccessOrdersByUser(openid);
        if (orderList != null) {
            modelMap.put("success", true);
            modelMap.put("successOrderList", orderList);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/notifier")
    public Map<String, Object> getLastNotification() {
        Map<String, Object> modelMap = new HashMap<>();
        Notification notification = notificationService.getLastNotification();
        modelMap.put("notification", notification);
        return modelMap;
    }

}
