package com.oki.stock.handler;

import com.google.gson.Gson;
import com.oki.stock.dto.HolderParam;
import com.oki.stock.dto.StockDto;
import com.oki.stock.entity.Holder;
import com.oki.stock.entity.Order;
import com.oki.stock.entity.User;
import com.oki.stock.service.HolderService;
import com.oki.stock.service.OrderService;
import com.oki.stock.service.StockService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpiderExchangeMessageReceiver {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HolderService holderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            logger.info(message);
            Gson gson = new Gson();
            Map<String, String> msgMap = new HashMap<>();
            msgMap = gson.fromJson(message, msgMap.getClass());

            Integer stockId = Integer.parseInt(msgMap.get("stock_id"));
            String stockName = msgMap.get("stock_name");
            String orderId = msgMap.get("order_id");
            String openid = msgMap.get("openid");
            String quotePrice = msgMap.get("quote_price");
            String quoteNums = msgMap.get("quote_nums");
            String stockScope = msgMap.get("stock_scope");
            String orderType = msgMap.get("order_type");
            String exchangePrice = msgMap.get("exchange_price");
            String exchangeTime = msgMap.get("exchange_time");

            BigDecimal lastPrice = new BigDecimal(exchangePrice).divide(new BigDecimal(1000));
            Date time = Utils.transferToDate(Long.parseLong(exchangeTime) * 1000);

            Order order = new Order();
            order.setOrderId(Integer.parseInt(orderId));
            order.setOrderStatus("1");
            order.setExchangePrice(lastPrice);
            order.setExchangeTime(time);

            if (orderService.modifyOrder(order)) {
                String orderKey = stockName + ":" + orderId;
                if (redisTemplate.hasKey(orderKey)) {
                    redisTemplate.delete(orderKey);
                }

                User user = userService.getUserByOpenid(openid);

                HolderParam holderParam = new HolderParam();
                holderParam.setOpenid(openid);
                holderParam.setStockId(stockId);

                Holder holder = holderService.getUserHolder(holderParam);
                if (holder != null && holder.getHolderId() > 0) {

                    BigDecimal originalNums = new BigDecimal(holder.getStockNums());
                    BigDecimal originalPrice = holder.getCostPrice();

                    StockDto stockDto = stockService.getStock(stockId);
                    BigDecimal currentPrice = new BigDecimal(stockDto.getCurrentPrice());

                    BigDecimal lastNums = null;
                    BigDecimal costPrice = null;

                    if (orderType.equals("0")) {

                        lastNums = new BigDecimal(quoteNums).add(originalNums);
                        costPrice = Utils.calcCostTotalPrice(originalPrice, originalNums).add(lastPrice.multiply(new BigDecimal(quoteNums))).divide(lastNums, 2, RoundingMode.HALF_UP);

                    } else if (orderType.equals("1")) {

                        lastNums = originalNums.subtract(new BigDecimal(quoteNums));
                        if (lastNums.signum() < 0)
                            return;

                        if (lastNums.signum() == 0) {
                            if (holderService.dropStockHolder(holder.getHolderId())) {
                                lastNums = new BigDecimal(quoteNums);
                                costPrice = Utils.calcCostTotalPrice(lastPrice, lastNums);

                                processRestDollar4Sell(stockScope, user, costPrice);
                                userService.modifyUserAssets(user);
                            }
                            return;
                        }

                        costPrice = Utils.calcCostTotalPrice(originalPrice, originalNums).subtract(lastPrice.multiply(new BigDecimal(quoteNums))).divide(lastNums, 2, RoundingMode.HALF_UP);
                    }

                    BigDecimal profitAmount = Utils.calcProfitAmount(currentPrice, costPrice, lastNums);
                    BigDecimal profitPercent = Utils.calcProfitPercent(profitAmount, costPrice, lastNums);

                    holder.setCostPrice(costPrice);
                    holder.setStockNums(lastNums.intValue());
                    holder.setProfitAmount(profitAmount);

                    if (profitAmount.signum() >= 0) {
                        holder.setProfitPercent("+" + profitPercent.toString() + "%");
                    } else {
                        holder.setProfitPercent(profitPercent.toString() + "%");
                    }

                    if (holderService.modifyStockHolder(holder)) {

                        lastNums = new BigDecimal(quoteNums);
                        costPrice = Utils.calcCostTotalPrice(lastPrice, lastNums);

                        if (orderType.equals("0")) {
                            BigDecimal orderPrice = new BigDecimal(quotePrice).multiply(lastNums);
                            processRestDollar4Buy(stockScope, user, costPrice, orderPrice);
                        } else if (orderType.equals("1")) {

                            processRestDollar4Sell(stockScope, user, costPrice);
                        }
                        userService.modifyUserAssets(user);
                    }

                } else {
                    holder = new Holder();
                    holder.setOpenid(openid);
                    holder.setCostPrice(lastPrice);
                    holder.setStockId(stockId);
                    holder.setStockNums(Integer.parseInt(quoteNums));
                    holder.setStockScope(stockScope);
                    holder.setProfitAmount(new BigDecimal(0));
                    holder.setProfitPercent("+0.00%");

                    if (holderService.addStockHolder(holder)) {

                        BigDecimal lastNums = new BigDecimal(quoteNums);
                        BigDecimal costPrice = Utils.calcCostTotalPrice(lastPrice, lastNums);
                        BigDecimal orderPrice = new BigDecimal(quotePrice).multiply(lastNums);

                        processRestDollar4Buy(stockScope, user, costPrice, orderPrice);

                        userService.modifyUserAssets(user);
                    }
                }
            }
        }

    }

    private void processRestDollar4Sell(String stockScope, User user, BigDecimal costPrice) {
        if (stockScope.equals("hk")) {
            BigDecimal restDollar = user.getHkRestDollar().add(costPrice);
            user.setHkRestDollar(restDollar);

        } else if (stockScope.equals("us")) {
            BigDecimal restDollar = user.getUsRestDollar().add(costPrice);
            user.setUsRestDollar(restDollar);
        }
    }

    private void processRestDollar4Buy(String stockScope, User user, BigDecimal costPrice, BigDecimal orderPrice) {
        if (stockScope.equals("hk")) {
            BigDecimal hkFrozenCapital = user.getHkFrozenCapital();
            BigDecimal restDollar = user.getHkRestDollar().add(hkFrozenCapital).subtract(costPrice);
            user.setHkRestDollar(restDollar);
            user.setHkFrozenCapital(hkFrozenCapital.subtract(orderPrice));

        } else if (stockScope.equals("us")) {
            BigDecimal usFrozenCapital = user.getUsFrozenCapital();
            BigDecimal restDollar = user.getUsRestDollar().add(usFrozenCapital).subtract(costPrice);
            user.setUsRestDollar(restDollar);
            user.setUsFrozenCapital(usFrozenCapital.subtract(orderPrice));
        }
    }

}
