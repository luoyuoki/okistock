package com.oki.stock.controller;

import com.oki.stock.dto.HolderParam;
import com.oki.stock.dto.StockDto;
import com.oki.stock.entity.*;
import com.oki.stock.service.HolderService;
import com.oki.stock.service.OrderService;
import com.oki.stock.service.StockService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.SpringContextUtil;
import com.oki.stock.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockMainController {

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HolderService holderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/hk")
    public Map<String, Object> getHkStockList() {
        Map<String, Object> modelMap = new HashMap<>();
        List<StockDto> stockList = stockService.getHkStocks();
        if (stockList != null) {
            modelMap.put("success", true);
            modelMap.put("stockList", stockList);

            TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);
            modelMap.put("trading", tradingFlag.getHkTrading());
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/us")
    public Map<String, Object> getUsStockList() {
        Map<String, Object> modelMap = new HashMap<>();
        List<StockDto> stockList = stockService.getUsStocks();
        if (stockList != null) {
            modelMap.put("success", true);
            modelMap.put("stockList", stockList);

            TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);
            modelMap.put("trading", tradingFlag.getUsTrading());
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping(value = "/get")
    public Map<String, Object> getStockById(@RequestParam("stockId") Integer stockId, @RequestParam("openid") String openid) {
        Map<String, Object> modelMap = new HashMap<>();
        Stock stock = stockService.getStockById(stockId);

        HolderParam holderParam = new HolderParam();
        holderParam.setOpenid(openid);
        holderParam.setStockId(stockId);
        Holder holder = holderService.getUserHolder(holderParam);
        Integer mostSell = 0;
        Integer mostBuy = 0;
        if (holder != null) {
            mostSell = holder.getStockNums();
        }

        if (stock != null) {
            String stockScope = stock.getStockScope();
            BigDecimal roundNums = new BigDecimal(stock.getRoundNums());
            User user = userService.getUserByOpenid(openid);
            BigDecimal stockPrice = new BigDecimal(stock.getCurrentPrice());
            if (stockScope.equals("hk")) {
                BigDecimal hkRest = user.getHkRestDollar();
                mostBuy = hkRest.divideToIntegralValue(stockPrice).divideToIntegralValue(roundNums).multiply(roundNums).intValue();
            } else if (stockScope.equals("us")) {
                BigDecimal usRest = user.getUsRestDollar();
                mostBuy = usRest.divideToIntegralValue(stockPrice).intValue();
            }

            modelMap.put("success", true);
            modelMap.put("stock", stock);
            modelMap.put("mostSell", mostSell);
            modelMap.put("mostBuy", mostBuy);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @PostMapping(value = "/order")
    public Map<String, Object> addNewOrder(@RequestBody Order order) {
        Map<String, Object> modelMap = new HashMap<>();

        if (orderService.addOrder(order)) {
            Map<String, String> redisMap = new HashMap<>();
            Integer orderId = order.getOrderId();
            String quotePrice = order.getQuotePrice();
            Integer quoteNums = order.getQuoteNums();
            String stockScope = order.getStockScope();
            String openid = order.getOpenid();
            String orderType = order.getOrderType();
            redisMap.put("order_id", String.valueOf(orderId));
            redisMap.put("quote_price", quotePrice);
            redisMap.put("quote_nums", String.valueOf(quoteNums));
            redisMap.put("order_type", orderType);
            redisMap.put("stock_scope", stockScope);
            redisMap.put("commit_time", String.valueOf(order.getCommitTime().getTime() / 1000));
            redisMap.put("stock_id", String.valueOf(order.getStockId()));
            redisMap.put("openid", openid);
            redisTemplate.opsForHash().putAll(order.getStockName() + ":" + orderId, redisMap);

            if (orderType.equals("0")) {
                BigDecimal costTotalPrice = Utils.calcCostTotalPrice(new BigDecimal(quotePrice), new BigDecimal(quoteNums));
                User user = userService.getUserByOpenid(openid);
                if (stockScope.equals("hk")) {
                    user.setHkFrozenCapital(user.getHkFrozenCapital().add(costTotalPrice));
                    user.setHkRestDollar(user.getHkRestDollar().subtract(costTotalPrice));
                } else if (stockScope.equals("us")) {
                    user.setUsFrozenCapital(user.getUsFrozenCapital().add(costTotalPrice));
                    user.setUsRestDollar(user.getUsRestDollar().subtract(costTotalPrice));
                }
                if (userService.modifyUserAssets(user)) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                }
            } else {
                modelMap.put("success", true);
            }
        } else {
            modelMap.put("success", false);
        }

        return modelMap;
    }

}
