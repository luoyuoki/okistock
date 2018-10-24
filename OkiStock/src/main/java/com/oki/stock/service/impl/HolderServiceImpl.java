package com.oki.stock.service.impl;

import com.oki.stock.common.CodeMsg;
import com.oki.stock.dao.HolderDao;
import com.oki.stock.dto.HolderDTO;
import com.oki.stock.dto.HolderParamDTO;
import com.oki.stock.entity.Holder;
import com.oki.stock.exception.StockServerException;
import com.oki.stock.service.HolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HolderServiceImpl implements HolderService {

    @Autowired
    private HolderDao holderDao;

    @Override
    @Transactional
    public boolean addStockHolder(Holder holder) {
        if (StringUtils.isEmpty(holder.getOpenid())) {
            throw new StockServerException(CodeMsg.ADD_STOCK_HOLDER_ERROR);
        }
        int effectedNums = holderDao.insertStockHolder(holder);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.ADD_STOCK_HOLDER_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean modifyStockHolder(Holder holder) {
        int effectedNums = holderDao.updateStockHolder(holder);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.MODIFY_STOCK_HOLDER_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean dropStockHolder(Integer holderId) {
        int effectedNums = holderDao.deleteStockHolder(holderId);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.DROP_STOCK_HOLDER_ERROR);
        }
    }

    @Override
    public List<HolderDTO> getUserHoldersByOpenid(String openid) {
        List<Holder> holderList = holderDao.queryUserHoldersByOpenid(openid);
        if (holderList != null) {
            List<HolderDTO> hdList = new ArrayList<>();
            for (Holder holder : holderList) {
                HolderDTO hd = new HolderDTO();
                hd.setStockId(holder.getStockId());
                hd.setStockName(holder.getStockName());
                hd.setStockScope(holder.getStockScope());
                hd.setCostPrice(holder.getCostPrice().toString());
                hd.setStockNums(holder.getStockNums());
                hd.setProfitAmount(holder.getProfitAmount().toString());

                String profitPercent = holder.getProfitPercent();
                hd.setProfitPercent(profitPercent);
                if (profitPercent.startsWith("+")) {
                    hd.setRise(true);
                } else {
                    hd.setRise(false);
                }
                hdList.add(hd);
            }
            return hdList;
        }

        return null;
    }

    @Override
    public List<Holder> getUserHoldersByStockName(String stockName) {
        return holderDao.queryUserHolderByStockName(stockName);
    }

    @Override
    public Holder getUserHolder(HolderParamDTO holderParamDTO) {
        return holderDao.queryUserHolder(holderParamDTO);
    }

}
