package com.oki.stock.service.impl;

import com.oki.stock.dao.OrderDao;
import com.oki.stock.dto.NewOrderDto;
import com.oki.stock.dto.SuccessOrderDto;
import com.oki.stock.entity.Order;
import com.oki.stock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public boolean addOrder(Order order) {
        if (!StringUtils.isEmpty(order.getOpenid())) {
            order.setOrderStatus("0");
            order.setCommitTime(new Date());
            try {
                int effectedNums = orderDao.insertOrder(order);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("下单失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("下单失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("下单失败openid为空");
        }
    }

    @Override
    public boolean modifyOrder(Order order) {
        if (order.getOrderId() > 0) {
            try {
                int effectedNums = orderDao.updateOrder(order);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新订单失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新订单失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("订单ID不能为空");
        }
    }

    @Override
    public List<NewOrderDto> getNewOrdersByUser(String openid) {
        List<Order> orders = orderDao.queryNewOrders(openid);
        if (orders != null) {
            List<NewOrderDto> noList = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            for (Order order : orders) {
                NewOrderDto no = new NewOrderDto();
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
    public List<SuccessOrderDto> getSuccessOrdersByUser(String openid) {
        List<Order> orders = orderDao.querySuccessOrders(openid);
        if (orders != null) {
            List<SuccessOrderDto> soList = new ArrayList<>();
            for (Order order : orders) {
                SuccessOrderDto so = new SuccessOrderDto();
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
    public boolean modifyOrderStatusToFail() {
        try {
            orderDao.updateOrderStatusToFail();
        } catch (Exception e) {
            throw new RuntimeException("更新过期订单失败：" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean dropOrderByFail() {
        try {
            orderDao.deleteOrderByFail();
        } catch (Exception e) {
            throw new RuntimeException("删除过期订单失败：" + e.getMessage());
        }
        return true;
    }
}
