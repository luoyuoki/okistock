package com.oki.stock.service;

import com.oki.stock.dto.ProfitDTO;
import com.oki.stock.dto.UserDTO;
import com.oki.stock.entity.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    User getUserByOpenid(String openid);

    UserDTO getUserInfoByOpenid(String openid);

    boolean modifyUserAssets(User user);

    List<ProfitDTO> getUsersProfitAmount(String scope);

    void modifyUserHkAssetsBatch(List<ProfitDTO> usersProfit);

    void modifyUserUsAssetsBatch(List<ProfitDTO> usersProfit);
}
