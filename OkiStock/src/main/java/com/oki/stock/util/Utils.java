package com.oki.stock.util;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {

    public static Date transferToDate(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeValue = dateFormat.format(time);
        try {
            return dateFormat.parse(timeValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigDecimal calcCostTotalPrice(BigDecimal price, BigDecimal nums) {
        return price.multiply(nums);
    }

    public static BigDecimal calcProfitAmount(BigDecimal currentPrice, BigDecimal costPrice, BigDecimal nums) {
        return currentPrice.multiply(nums).subtract(calcCostTotalPrice(costPrice, nums));
    }

    public static BigDecimal calcProfitPercent(BigDecimal profitAmount, BigDecimal costPrice, BigDecimal nums) {
        return profitAmount.divide(calcCostTotalPrice(costPrice, nums), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2);
    }

    public static <T> List<T> json2List(String jsonStr, Class<T> objectClass) {
        if (jsonStr != null && !jsonStr.trim().equals("")) {
            return JSONArray.parseArray(jsonStr, objectClass);
        }
        return null;
    }
}
