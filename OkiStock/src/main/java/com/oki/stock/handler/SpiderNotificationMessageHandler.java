package com.oki.stock.handler;

import com.google.gson.Gson;
import com.oki.stock.dto.ProfitDTO;
import com.oki.stock.common.TradingFlag;
import com.oki.stock.service.OrderService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SpiderNotificationMessageHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${user.assets.hk}")
    private String hkAssets;

    @Value("${user.assets.us}")
    private String usAssets;

    public void handleMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            log.info(message);
            Gson gson = new Gson();
            Map<String, String> msgMap = new HashMap<>();
            msgMap = gson.fromJson(message, msgMap.getClass());

            String action = msgMap.get("spider");
            String scope = msgMap.get("scope");
            switch (action) {
                case "stop":

                    List<ProfitDTO> usersProfit = userService.getUsersProfitAmount(scope);
                    if (usersProfit != null && usersProfit.size() > 0) {
                        for (ProfitDTO userProfit : usersProfit) {
                            BigDecimal profitAmount = userProfit.getProfitAmount();

                            BigDecimal originalAssets = null;
                            if (scope.equals("hk")) {
                                originalAssets = new BigDecimal(hkAssets);
                            } else if (scope.equals("us")) {
                                originalAssets = new BigDecimal(usAssets);
                            }
                            BigDecimal currentAssets = originalAssets.add(profitAmount);
                            BigDecimal profitPercent = profitAmount.divide(originalAssets, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2);

                            userProfit.setAssets(currentAssets);

                            if (currentAssets.compareTo(originalAssets) >= 0) {
                                userProfit.setProfitPercent("+" + profitPercent.toString() + "%");
                            } else {
                                userProfit.setProfitPercent(profitPercent.toString() + "%");
                            }
                        }

                        if (scope.equals("hk")) {
                            userService.modifyUserHkAssetsBatch(usersProfit);
                        } else if (scope.equals("us")) {
                            userService.modifyUserUsAssetsBatch(usersProfit);
                        }
                    }
                    break;
                case "start": {
                    TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);

                    if (scope.equals("hk")) {
                        log.info("hk start...");
                        tradingFlag.setHkTrading(true);
                    } else if (scope.equals("us")) {
                        log.info("us start...");
                        tradingFlag.setUsTrading(true);
                    }

                    orderService.dropOrderByFail();
                    break;
                }
                case "finish": {
                    TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);

                    if (scope.equals("hk")) {
                        log.info("hk finish...");
                        tradingFlag.setHkTrading(false);
                    } else if (scope.equals("us")) {
                        log.info("us finish...");
                        tradingFlag.setUsTrading(false);
                    }
                    orderService.modifyOrderStatusToFail();
                    flushRedis();
                    break;
                }
            }
        }
    }

    private void flushRedis() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }
}
