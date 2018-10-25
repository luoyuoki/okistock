package com.oki.stock.cloud.user.server.dao;

import com.oki.stock.cloud.base.dto.ProfitDTO;
import com.oki.stock.cloud.base.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    int insertUser(User user);

    User queryUserByOpenid(String openid);

    int updateUserAssets(User user);

    List<ProfitDTO> queryUsersProfitAmount(String scope);

    void updateUserHkAssetsBatch(List<ProfitDTO> usersProfit);

    void updateUserUsAssetsBatch(List<ProfitDTO> usersProfit);
}