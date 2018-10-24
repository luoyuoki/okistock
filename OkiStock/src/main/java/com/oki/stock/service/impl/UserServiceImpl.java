package com.oki.stock.service.impl;

import com.oki.stock.common.CodeMsg;
import com.oki.stock.dao.UserDao;
import com.oki.stock.dto.ProfitDTO;
import com.oki.stock.dto.UserDTO;
import com.oki.stock.entity.User;
import com.oki.stock.exception.StockServerException;
import com.oki.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public boolean addUser(User user) {
        if (StringUtils.isEmpty(user.getOpenid()) || StringUtils.isEmpty(user.getNickName()) || StringUtils.isEmpty(user.getAvatarUrl())) {
            throw new StockServerException(CodeMsg.ADD_USER_INFO_EMPTY);
        }
        int effectedNums = userDao.insertUser(user);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.ADD_USER_ERROR);
        }
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userDao.queryUserByOpenid(openid);
    }

    @Override
    public UserDTO getUserInfoByOpenid(String openid) {
        User user = userDao.queryUserByOpenid(openid);
        if (user != null) {
            UserDTO ud = new UserDTO();
            ud.setOpenid(user.getOpenid());
            ud.setNickName(user.getNickName());
            ud.setAvatarUrl(user.getAvatarUrl());
            ud.setHkAssets(user.getHkAssets().toString());
            ud.setHkRestDollar(user.getHkRestDollar().toString());
            ud.setHkProfitAmount(user.getHkProfitAmount().toString());
            ud.setHkFrozenCapital(user.getHkFrozenCapital().toString());

            String hkProfitPercent = user.getHkProfitPercent();
            ud.setHkProfitPercent(hkProfitPercent);
            if (hkProfitPercent.startsWith("+")) {
                ud.setHkRise(true);
            } else {
                ud.setHkRise(false);
            }

            ud.setUsAssets(user.getUsAssets().toString());
            ud.setUsRestDollar(user.getUsRestDollar().toString());
            ud.setUsProfitAmount(user.getUsProfitAmount().toString());
            ud.setUsFrozenCapital(user.getUsFrozenCapital().toString());

            String usProfitPercent = user.getUsProfitPercent();
            ud.setUsProfitPercent(usProfitPercent);
            if (usProfitPercent.startsWith("+")) {
                ud.setUsRise(true);
            } else {
                ud.setUsRise(false);
            }

            return ud;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean modifyUserAssets(User user) {
        int effectedNums = userDao.updateUserAssets(user);
        if (effectedNums > 0) {
            return true;
        } else {
            throw new StockServerException(CodeMsg.MODIFY_USER_ASSETS_ERROR);
        }
    }

    @Override
    public List<ProfitDTO> getUsersProfitAmount(String scope) {
        return userDao.queryUsersProfitAmount(scope);
    }

    @Override
    public void modifyUserHkAssetsBatch(List<ProfitDTO> usersProfit) {
        userDao.updateUserHkAssetsBatch(usersProfit);
    }

    @Override
    public void modifyUserUsAssetsBatch(List<ProfitDTO> usersProfit) {
        userDao.updateUserUsAssetsBatch(usersProfit);
    }

}
