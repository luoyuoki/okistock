package com.oki.stock.service;

import com.oki.stock.dto.HkRankDto;
import com.oki.stock.dto.UsRankDto;

import java.util.List;

public interface RankService {

    List<HkRankDto> getHkRankList();

    List<UsRankDto> getUsRankList();
}
