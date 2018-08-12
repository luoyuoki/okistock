package com.oki.stock.service;

import com.oki.stock.dto.NewOrderDto;
import com.oki.stock.dto.SuccessOrderDto;
import com.oki.stock.entity.Order;

import java.util.List;

public interface OrderService {

    boolean addOrder(Order order);

    boolean modifyOrder(Order order);

    List<NewOrderDto> getNewOrdersByUser(String openid);

    List<SuccessOrderDto> getSuccessOrdersByUser(String openid);

    boolean modifyOrderStatusToFail();

    boolean dropOrderByFail();

}
