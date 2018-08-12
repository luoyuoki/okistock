package com.oki.stock.service;

import com.oki.stock.dto.HolderDto;
import com.oki.stock.dto.HolderParam;
import com.oki.stock.entity.Holder;

import java.util.List;

public interface HolderService {

    boolean addStockHolder(Holder holder);

    boolean modifyStockHolder(Holder holder);

    boolean dropStockHolder(Integer holderId);

    List<HolderDto> getUserHoldersByOpenid(String openid);

    List<Holder> getUserHoldersByStockName(String stockName);

    Holder getUserHolder(HolderParam holderParam);
}
