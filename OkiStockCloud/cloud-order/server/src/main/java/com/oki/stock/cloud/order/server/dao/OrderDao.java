package com.oki.stock.cloud.order.server.dao;

import com.oki.stock.cloud.base.dto.HolderParamDTO;
import com.oki.stock.cloud.base.entity.Holder;
import com.oki.stock.cloud.base.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/26
 */
@Mapper
@Repository
public interface OrderDao {

    List<Holder> queryUserHoldersByOpenid(String openid);

    List<Holder> queryUserHolderByStockName(String stockName);

    List<Order> queryNewOrders(String openid);

    List<Order> querySuccessOrders(String openid);

    Holder queryUserHolder(HolderParamDTO holderParamDTO);
}
