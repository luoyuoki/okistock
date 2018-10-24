package com.oki.stock.dao;

import com.oki.stock.dto.ProfitDTO;
import com.oki.stock.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int insertUser(User user);

    User queryUserByOpenid(String openid);

    int updateUserAssets(User user);

    List<ProfitDTO> queryUsersProfitAmount(String scope);

    void updateUserHkAssetsBatch(List<ProfitDTO> usersProfit);

    void updateUserUsAssetsBatch(List<ProfitDTO> usersProfit);
}