package com.oki.stock.dao;

import com.oki.stock.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    int insertOrder(Order order);

    List<Order> queryNewOrders(String openid);

    List<Order> querySuccessOrders(String openid);

    int updateOrder(Order order);

    int updateOrderStatusToFail();

    int deleteOrderByFail();

}
