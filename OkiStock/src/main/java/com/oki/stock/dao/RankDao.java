package com.oki.stock.dao;

import com.oki.stock.dto.HkRankDTO;
import com.oki.stock.dto.UsRankDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankDao {

    List<HkRankDTO> queryHkRankList();

    List<UsRankDTO> queryUsRankList();
}
