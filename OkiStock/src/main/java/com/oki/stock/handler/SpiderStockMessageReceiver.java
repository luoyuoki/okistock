package com.oki.stock.handler;

import com.google.gson.Gson;
import com.oki.stock.entity.Holder;
import com.oki.stock.service.HolderService;
import com.oki.stock.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpiderStockMessageReceiver {

    @Autowired
    private HolderService holderService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            logger.info(message);
            Gson gson = new Gson();
            Map<String, String> msgMap = new HashMap<>();
            msgMap = gson.fromJson(message, msgMap.getClass());

            String stockName = msgMap.get("stock_name");
            BigDecimal currentPrice = new BigDecimal(msgMap.get("current_price"));

            List<Holder> holderList = holderService.getUserHoldersByStockName(stockName);
            if (holderList != null && holderList.size() > 0) {
                for (Holder holder : holderList) {
                    BigDecimal costPrice = holder.getCostPrice();
                    BigDecimal stockNums = new BigDecimal(holder.getStockNums());

                    BigDecimal profitAmount = Utils.calcProfitAmount(currentPrice, costPrice, stockNums);
                    BigDecimal profitPercent = Utils.calcProfitPercent(profitAmount, costPrice, stockNums);
                    holder.setProfitAmount(profitAmount);

                    if (profitAmount.signum() >= 0) {
                        holder.setProfitPercent("+" + profitPercent.toString() + "%");
                    } else {
                        holder.setProfitPercent(profitPercent.toString() + "%");
                    }

                    holderService.modifyStockHolder(holder);
                }
            }
        }
    }
}
