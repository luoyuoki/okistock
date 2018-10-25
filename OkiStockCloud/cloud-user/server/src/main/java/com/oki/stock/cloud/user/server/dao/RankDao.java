package com.oki.stock.cloud.user.server.dao;

import com.oki.stock.cloud.base.dto.HkRankDTO;
import com.oki.stock.cloud.base.dto.UsRankDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RankDao {

    List<HkRankDTO> queryHkRankList();

    List<UsRankDTO> queryUsRankList();
}
