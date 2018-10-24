package com.oki.stock.service.impl;

import com.oki.stock.common.CodeMsg;
import com.oki.stock.common.OrderStatus;
import com.oki.stock.dao.OrderDao;
import com.oki.stock.dto.NewOrderDTO;
import com.oki.stock.dto.SuccessOrderDTO;
import com.oki.stock.entity.Order;
import com.oki.stock.exception.StockServerException;
import com.oki.stock.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional
    public boolean addOrder(Order order) {
        order.setOrderStatus(OrderStatus.TRADE_WAIT.getStatus());
        order.setCommitTime(new Date());
        int effectedNums = orderDao.insertOrder(order);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.ADD_ORDER_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean modifyOrder(Order order) {
        int effectedNums = orderDao.updateOrder(order);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.MODIFY_ORDER_ERROR);
        }
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
    public boolean modifyOrderStatusToFail() {
        try {
            orderDao.updateOrderStatusToFail();
        } catch (Exception e) {
            log.error(e.toString());
            throw new StockServerException(CodeMsg.MODIFY_EXPIRED_ORDER_ERROR);
        }
        return true;
    }

    @Override
    public boolean dropOrderByFail() {
        try {
            orderDao.deleteOrderByFail();
        } catch (Exception e) {
            log.error(e.toString());
            throw new StockServerException(CodeMsg.DROP_EXPIRED_ORDER_ERROR);
        }
        return true;
    }
}
