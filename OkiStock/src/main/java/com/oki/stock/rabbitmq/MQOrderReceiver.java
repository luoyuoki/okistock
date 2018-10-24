package com.oki.stock.rabbitmq;

import com.oki.stock.common.OrderType;
import com.oki.stock.common.RespResult;
import com.oki.stock.entity.Order;
import com.oki.stock.entity.User;
import com.oki.stock.service.OrderService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MQOrderReceiver {

    private static final String REDIS_ORDER_KEY = "%s:%s";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = MQConfig.STOCK_ORDER_QUEUE)
    public void process(Order order) {

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

            redisTemplate.opsForHash().putAll(String.format(REDIS_ORDER_KEY, order.getStockName(), orderId), redisMap);

            if (orderType.equals(OrderType.BUY.getType())) {
                BigDecimal costTotalPrice = Utils.calcCostTotalPrice(new BigDecimal(quotePrice), new BigDecimal(quoteNums));
                User user = userService.getUserByOpenid(openid);
                if (stockScope.equals("hk")) {
                    user.setHkFrozenCapital(user.getHkFrozenCapital().add(costTotalPrice));
                    user.setHkRestDollar(user.getHkRestDollar().subtract(costTotalPrice));
                } else if (stockScope.equals("us")) {
                    user.setUsFrozenCapital(user.getUsFrozenCapital().add(costTotalPrice));
                    user.setUsRestDollar(user.getUsRestDollar().subtract(costTotalPrice));
                }
                if (!userService.modifyUserAssets(user)) {
                    log.error("mq modify user assets error");
                }
            }
        } else {
            log.error("mq add order error");
        }
    }
}
