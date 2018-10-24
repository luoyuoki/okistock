package com.oki.stock.service;

import com.oki.stock.dto.StockDTO;
import com.oki.stock.entity.Stock;

import java.util.List;

public interface StockService {

    List<StockDTO> getHkStocks();

    List<StockDTO> getUsStocks();

    Stock getStockById(Integer stockId);

    StockDTO getStock(Integer stockId);

}
