package com.oki.stock.handler;

import com.google.gson.Gson;
import com.oki.stock.entity.Holder;
import com.oki.stock.service.HolderService;
import com.oki.stock.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SpiderStockMessageHandler {

    @Autowired
    private HolderService holderService;

    public void handleMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            log.info(message);
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
