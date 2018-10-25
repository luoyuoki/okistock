package com.oki.stock.cloud.stock.server.dao;

import com.oki.stock.cloud.base.dto.StockDTO;
import com.oki.stock.cloud.base.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StockDao {

    List<Stock> queryHkStocks();

    List<Stock> queryUsStocks();

    Stock queryStockById(Integer stockId);

    StockDTO queryStock(Integer stockId);
}
