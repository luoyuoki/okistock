package com.oki.stock.cloud.stock.server.service;

import com.oki.stock.cloud.base.dto.StockDTO;
import com.oki.stock.cloud.base.entity.Stock;

import java.util.List;

public interface StockService {

    List<StockDTO> getHkStocks();

    List<StockDTO> getUsStocks();

    Stock getStockById(Integer stockId);

    StockDTO getStock(Integer stockId);

}
