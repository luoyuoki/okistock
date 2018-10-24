package com.oki.stock.dao;

import com.oki.stock.dto.StockDTO;
import com.oki.stock.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDao {

    List<Stock> queryHkStocks();

    Stock queryStockById(Integer stockId);

    StockDTO queryStock(Integer stockId);

    List<Stock> queryUsStocks();
}
