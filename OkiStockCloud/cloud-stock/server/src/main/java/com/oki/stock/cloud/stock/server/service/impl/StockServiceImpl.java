package com.oki.stock.cloud.stock.server.service.impl;

import com.oki.stock.cloud.base.dto.StockDTO;
import com.oki.stock.cloud.base.entity.Stock;
import com.oki.stock.cloud.stock.server.dao.StockDao;
import com.oki.stock.cloud.stock.server.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<StockDTO> getHkStocks() {
        List<Stock> stockList = stockDao.queryHkStocks();
        List<StockDTO> sdList = getStockList(stockList);
        if (sdList != null) return sdList;
        return null;
    }

    @Override
    public List<StockDTO> getUsStocks() {
        List<Stock> stockList = stockDao.queryUsStocks();
        List<StockDTO> sdList = getStockList(stockList);
        if (sdList != null) return sdList;
        return null;
    }

    private List<StockDTO> getStockList(List<Stock> stockList) {
        if (stockList != null && stockList.size() > 0) {
            List<StockDTO> sdList = new ArrayList<>();
            for (Stock stock : stockList) {
                StockDTO sd = new StockDTO();
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
    public StockDTO getStock(Integer stockId) {
        return stockDao.queryStock(stockId);
    }

}
