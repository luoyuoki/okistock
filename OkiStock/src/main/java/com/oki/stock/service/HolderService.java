package com.oki.stock.service;

import com.oki.stock.dto.HolderDTO;
import com.oki.stock.dto.HolderParamDTO;
import com.oki.stock.entity.Holder;

import java.util.List;

public interface HolderService {

    boolean addStockHolder(Holder holder);

    boolean modifyStockHolder(Holder holder);

    boolean dropStockHolder(Integer holderId);

    List<HolderDTO> getUserHoldersByOpenid(String openid);

    List<Holder> getUserHoldersByStockName(String stockName);

    Holder getUserHolder(HolderParamDTO holderParamDTO);

}
