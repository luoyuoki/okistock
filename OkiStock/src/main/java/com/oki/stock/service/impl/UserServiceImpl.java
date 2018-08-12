package com.oki.stock.service.impl;

import com.oki.stock.dao.UserDao;
import com.oki.stock.dto.ProfitDto;
import com.oki.stock.dto.UserDto;
import com.oki.stock.entity.User;
import com.oki.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public boolean addUser(User user) {
        if (!StringUtils.isEmpty(user.getOpenid()) && !StringUtils.isEmpty(user.getNickName()) && !StringUtils.isEmpty(user.getAvatarUrl())) {
            user.setUpdateTime(new Date());
            try {
                int effectedNums = userDao.insertUser(user);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("添加新用户失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("添加新用户失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("添加新用户关键信息为空");
        }
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userDao.queryUserByOpenid(openid);
    }

    @Override
    public UserDto getUserInfoByOpenid(String openid) {
        User user = userDao.queryUserByOpenid(openid);
        if (user != null) {
            UserDto ud = new UserDto();
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
    public boolean modifyUserAssets(User user) {
        if (user.getUserId() > 0) {
            try {
                user.setUpdateTime(new Date());
                int effectedNums = userDao.updateUserAssets(user);
                if (effectedNums > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新资产失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新资产失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("用户ID错误");
        }
    }

    @Override
    public List<ProfitDto> getUsersProfitAmount(String scope) {
        return userDao.queryUsersProfitAmount(scope);
    }

    @Override
    public void modifyUserHkAssetsBatch(List<ProfitDto> usersProfit) {
        userDao.updateUserHkAssetsBatch(usersProfit);
    }

    @Override
    public void modifyUserUsAssetsBatch(List<ProfitDto> usersProfit) {
        userDao.updateUserUsAssetsBatch(usersProfit);
    }

}
