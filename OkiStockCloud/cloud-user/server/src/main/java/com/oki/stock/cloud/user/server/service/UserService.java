package com.oki.stock.cloud.user.server.service;

import com.oki.stock.cloud.base.dto.ProfitDTO;
import com.oki.stock.cloud.base.dto.UserDTO;
import com.oki.stock.cloud.base.entity.User;

import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/23
 */
public interface UserService {

    boolean addUser(User user);

    User getUserByOpenid(String openid);

    UserDTO getUserInfoByOpenid(String openid);

    boolean modifyUserAssets(User user);

    List<ProfitDTO> getUsersProfitAmount(String scope);

    void modifyUserHkAssetsBatch(List<ProfitDTO> usersProfit);

    void modifyUserUsAssetsBatch(List<ProfitDTO> usersProfit);
}

