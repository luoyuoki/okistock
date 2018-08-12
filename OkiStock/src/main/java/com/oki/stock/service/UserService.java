package com.oki.stock.service;

import com.oki.stock.dto.ProfitDto;
import com.oki.stock.dto.UserDto;
import com.oki.stock.entity.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    User getUserByOpenid(String openid);

    UserDto getUserInfoByOpenid(String openid);

    boolean modifyUserAssets(User user);

    List<ProfitDto> getUsersProfitAmount(String scope);

    void modifyUserHkAssetsBatch(List<ProfitDto> usersProfit);

    void modifyUserUsAssetsBatch(List<ProfitDto> usersProfit);
}
