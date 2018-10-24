package com.oki.stock.service;

import com.oki.stock.dto.NewOrderDTO;
import com.oki.stock.dto.SuccessOrderDTO;
import com.oki.stock.entity.Order;

import java.util.List;

public interface OrderService {

    boolean addOrder(Order order);

    boolean modifyOrder(Order order);

    List<NewOrderDTO> getNewOrdersByUser(String openid);

    List<SuccessOrderDTO> getSuccessOrdersByUser(String openid);

    boolean modifyOrderStatusToFail();

    boolean dropOrderByFail();

}
