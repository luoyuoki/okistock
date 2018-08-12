package com.oki.stock.service.impl;

import com.oki.stock.dao.HolderDao;
import com.oki.stock.dto.HolderDto;
import com.oki.stock.dto.HolderParam;
import com.oki.stock.entity.Holder;
import com.oki.stock.service.HolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HolderServiceImpl implements HolderService {

    @Autowired
    private HolderDao holderDao;

    @Transactional
    @Override
    public boolean addStockHolder(Holder holder) {
        if (!StringUtils.isEmpty(holder.getOpenid())) {
            holder.setUpdateTime(new Date());
            try {
                int effectedNums = holderDao.insertStockHolder(holder);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("增加持仓股票失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("增加持仓股票失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("增加持仓股票openid为空");
        }
    }

    @Override
    public boolean modifyStockHolder(Holder holder) {
        if (holder.getHolderId() > 0) {
            try {
                holder.setUpdateTime(new Date());
                int effectedNums = holderDao.updateStockHolder(holder);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新持仓失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新持仓失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("持仓ID不能为空");
        }
    }

    @Override
    public boolean dropStockHolder(Integer holderId) {
        if (holderId > 0) {
            try {
                int effectedNums = holderDao.deleteStockHolder(holderId);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除持仓失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除持仓失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("持仓ID错误");
        }
    }

    @Override
    public List<HolderDto> getUserHoldersByOpenid(String openid) {
        List<Holder> holderList = holderDao.queryUserHoldersByOpenid(openid);
        if (holderList != null) {
            List<HolderDto> hdList = new ArrayList<>();
            for (Holder holder : holderList) {
                HolderDto hd = new HolderDto();
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
    public Holder getUserHolder(HolderParam holderParam) {
        return holderDao.queryUserHolder(holderParam);
    }
}
