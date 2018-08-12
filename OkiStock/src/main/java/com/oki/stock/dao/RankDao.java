package com.oki.stock.dao;

import com.oki.stock.dto.HkRankDto;
import com.oki.stock.dto.UsRankDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankDao {

    List<HkRankDto> queryHkRankList();

    List<UsRankDto> queryUsRankList();
}
