package com.oki.stock.service.impl;

import com.oki.stock.dao.StockDao;
import com.oki.stock.dto.StockDto;
import com.oki.stock.entity.Stock;
import com.oki.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<StockDto> getHkStocks() {
        List<Stock> stockList = stockDao.queryHkStocks();
        List<StockDto> sdList = getStockList(stockList);
        if (sdList != null) return sdList;
        return null;
    }

    @Override
    public List<StockDto> getUsStocks() {
        List<Stock> stockList = stockDao.queryUsStocks();
        List<StockDto> sdList = getStockList(stockList);
        if (sdList != null) return sdList;
        return null;
    }

    private List<StockDto> getStockList(List<Stock> stockList) {
        if (stockList != null && stockList.size() > 0) {
            List<StockDto> sdList = new ArrayList<>();
            for (Stock stock : stockList) {
                StockDto sd = new StockDto();
                sd.setStockId(stock.getStockId());
                sd.setStockName(stock.getStockName());
                sd.setCurrentPrice(stock.getCurrentPrice());

                String changePercent = stock.getChangePercent();
                if (changePercent != null && changePercent.startsWith("+")) {
                    sd.setRise(true);
                } else {
                    sd.setRise(false);
                }
                sd.setChangePercent(changePercent);
                sdList.add(sd);
            }
            return sdList;
        }
        return null;
    }

    @Override
    public Stock getStockById(Integer stockId) {
        Stock stock = stockDao.queryStockById(stockId);
        if (stock != null) {
            String changePercent = stock.getChangePercent();
            if (changePercent != null && changePercent.startsWith("+")) {
                stock.setRise(true);
            } else {
                stock.setRise(false);
            }
            return stock;
        }
        return null;
    }

    @Override
    public StockDto getStock(Integer stockId) {
        return stockDao.queryStock(stockId);
    }

}
