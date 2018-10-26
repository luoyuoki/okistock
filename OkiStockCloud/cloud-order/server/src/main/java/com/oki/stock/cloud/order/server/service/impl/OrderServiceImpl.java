package com.oki.stock.cloud.order.server.service.impl;

import com.oki.stock.cloud.base.dto.HolderDTO;
import com.oki.stock.cloud.base.dto.HolderParamDTO;
import com.oki.stock.cloud.base.dto.NewOrderDTO;
import com.oki.stock.cloud.base.dto.SuccessOrderDTO;
import com.oki.stock.cloud.base.entity.Holder;
import com.oki.stock.cloud.base.entity.Order;
import com.oki.stock.cloud.order.server.dao.OrderDao;
import com.oki.stock.cloud.order.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 0ki
 * @date 2018/10/26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<HolderDTO> getUserHoldersByOpenid(String openid) {
        List<Holder> holderList = orderDao.queryUserHoldersByOpenid(openid);
        if (holderList != null) {
            return holderList.stream().map(holder -> {
                HolderDTO hd = new HolderDTO();
                hd.setStockId(holder.getStockId());
                hd.setStockName(holder.getStockName());
                hd.setStockScope(holder.getStockScope());
                hd.setCostPrice(holder.getCostPrice().toString());
                hd.setStockNums(holder.getStockNums());
                hd.setProfitAmount(holder.getProfitAmount().toString());

                String profitPercent = holder.getProfitPercent();
                hd.setProfitPercent(profitPercent);
                if (profitPercent.startsWith("+")) {
                    hd.setRise(true);
                } else {
                    hd.setRise(false);
                }
                return hd;
            }).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<NewOrderDTO> getNewOrdersByUser(String openid) {
        List<Order> orders = orderDao.queryNewOrders(openid);
        if (orders != null) {
            List<NewOrderDTO> noList = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            for (Order order : orders) {
                NewOrderDTO no = new NewOrderDTO();
                no.setOrderId(order.getOrderId());
                no.setStockName(order.getStockName());
                no.setQuotePrice(order.getQuotePrice());
                no.setQuoteNums(order.getQuoteNums());
                no.setOrderStatus(order.getOrderStatus());
                no.setOrderType(order.getOrderType());
                no.setCommitTime(formatter.format(order.getCommitTime()));
                noList.add(no);
            }
            return noList;
        }
        return null;
    }

    @Override
    public List<SuccessOrderDTO> getSuccessOrdersByUser(String openid) {
        List<Order> orders = orderDao.querySuccessOrders(openid);
        if (orders != null) {
            List<SuccessOrderDTO> soList = new ArrayList<>();
            for (Order order : orders) {
                SuccessOrderDTO so = new SuccessOrderDTO();
                so.setOrderId(order.getOrderId());
                so.setOrderType(order.getOrderType());
                so.setStockName(order.getStockName());
                so.setExchangePrice(order.getExchangePrice().toString());
                so.setQuoteNums(order.getQuoteNums());
                so.setExchangeTime(DateFormat.getDateInstance(DateFormat.SHORT).format(order.getExchangeTime()));
                soList.add(so);
            }
            return soList;
        }
        return null;
    }

    @Override
    public List<Holder> getUserHoldersByStockName(String stockName) {
        return null;
    }

    @Override
    public Holder getUserHolder(HolderParamDTO holderParamDTO) {
        return null;
    }
}
