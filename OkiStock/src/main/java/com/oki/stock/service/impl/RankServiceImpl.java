package com.oki.stock.service.impl;

import com.oki.stock.dao.RankDao;
import com.oki.stock.dto.HkRankDto;
import com.oki.stock.dto.UsRankDto;
import com.oki.stock.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankDao rankDao;

    @Override
    public List<HkRankDto> getHkRankList() {
        List<HkRankDto> rankList = rankDao.queryHkRankList();
        for (HkRankDto rank : rankList) {
            if (rank.getHkProfitPercent().startsWith("+")) {
                rank.setRise(true);
            } else {
                rank.setRise(false);
            }
        }
        return rankList;
    }

    @Override
    public List<UsRankDto> getUsRankList() {
        List<UsRankDto> rankList = rankDao.queryUsRankList();
        for (UsRankDto rank : rankList) {
            if (rank.getUsProfitPercent().startsWith("+")) {
                rank.setRise(true);
            } else {
                rank.setRise(false);
            }
        }
        return rankList;
    }
}
