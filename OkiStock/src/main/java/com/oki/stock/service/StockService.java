package com.oki.stock.service;

import com.oki.stock.dto.StockDto;
import com.oki.stock.entity.Stock;

import java.util.List;

public interface StockService {

    List<StockDto> getHkStocks();

    List<StockDto> getUsStocks();

    Stock getStockById(Integer stockId);

    StockDto getStock(Integer stockId);

}
