package com.oki.stock.cloud.user.server.service.impl;

import com.oki.stock.cloud.base.dto.HkRankDTO;
import com.oki.stock.cloud.base.dto.UsRankDTO;
import com.oki.stock.cloud.user.server.dao.RankDao;
import com.oki.stock.cloud.user.server.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankDao rankDao;

    @Override
    public List<HkRankDTO> getHkRankList() {
        List<HkRankDTO> rankList = rankDao.queryHkRankList();
        for (HkRankDTO rank : rankList) {
            if (rank.getHkProfitPercent().startsWith("+")) {
                rank.setRise(true);
            } else {
                rank.setRise(false);
            }
        }
        return rankList;
    }

    @Override
    public List<UsRankDTO> getUsRankList() {
        List<UsRankDTO> rankList = rankDao.queryUsRankList();
        for (UsRankDTO rank : rankList) {
            if (rank.getUsProfitPercent().startsWith("+")) {
                rank.setRise(true);
            } else {
                rank.setRise(false);
            }
        }
        return rankList;
    }
}
