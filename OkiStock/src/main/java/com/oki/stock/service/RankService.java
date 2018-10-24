package com.oki.stock.service;

import com.oki.stock.dto.HkRankDTO;
import com.oki.stock.dto.UsRankDTO;

import java.util.List;

public interface RankService {

    List<HkRankDTO> getHkRankList();

    List<UsRankDTO> getUsRankList();
}
