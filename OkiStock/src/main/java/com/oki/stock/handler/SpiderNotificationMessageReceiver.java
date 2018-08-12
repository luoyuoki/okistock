package com.oki.stock.handler;

import com.google.gson.Gson;
import com.oki.stock.dto.ProfitDto;
import com.oki.stock.entity.TradingFlag;
import com.oki.stock.service.OrderService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SpiderNotificationMessageReceiver {

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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            logger.info(message);
            Gson gson = new Gson();
            Map<String, String> msgMap = new HashMap<>();
            msgMap = gson.fromJson(message, msgMap.getClass());

            String action = msgMap.get("spider");
            String scope = msgMap.get("scope");
            switch (action) {
                case "stop":

                    List<ProfitDto> usersProfit = userService.getUsersProfitAmount(scope);
                    if (usersProfit != null && usersProfit.size() > 0) {
                        for (ProfitDto userProfit : usersProfit) {
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
                        tradingFlag.setHkTrading(true);
                    } else if (scope.equals("us")) {
                        tradingFlag.setUsTrading(true);
                    }

                    orderService.dropOrderByFail();
                    break;
                }
                case "finish": {
                    TradingFlag tradingFlag = SpringContextUtil.getBean(TradingFlag.class);

                    if (scope.equals("hk")) {
                        tradingFlag.setHkTrading(false);
                    } else if (scope.equals("us")) {
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
        redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}
