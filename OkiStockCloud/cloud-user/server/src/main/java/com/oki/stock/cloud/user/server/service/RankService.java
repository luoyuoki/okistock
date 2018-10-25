package com.oki.stock.cloud.user.server.service;


import com.oki.stock.cloud.base.dto.HkRankDTO;
import com.oki.stock.cloud.base.dto.UsRankDTO;

import java.util.List;

public interface RankService {

    List<HkRankDTO> getHkRankList();

    List<UsRankDTO> getUsRankList();
}
