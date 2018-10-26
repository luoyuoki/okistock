package com.oki.stock.cloud.order.server.service;

import com.oki.stock.cloud.base.dto.HolderDTO;
import com.oki.stock.cloud.base.dto.HolderParamDTO;
import com.oki.stock.cloud.base.dto.NewOrderDTO;
import com.oki.stock.cloud.base.dto.SuccessOrderDTO;
import com.oki.stock.cloud.base.entity.Holder;

import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/26
 */
public interface OrderService {

    List<HolderDTO> getUserHoldersByOpenid(String openid);

    List<NewOrderDTO> getNewOrdersByUser(String openid);

    List<SuccessOrderDTO> getSuccessOrdersByUser(String openid);

    List<Holder> getUserHoldersByStockName(String stockName);

    Holder getUserHolder(HolderParamDTO holderParamDTO);
}
