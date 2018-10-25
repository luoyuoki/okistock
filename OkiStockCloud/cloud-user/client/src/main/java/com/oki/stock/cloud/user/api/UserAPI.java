package com.oki.stock.cloud.user.api;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.user.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private UserClient userClient;

    @GetMapping("/my")
    public RespResult getMyMainInfo(String openid) {
        return userClient.getMyMainInfo(openid);
    }

    @GetMapping("/login")
    public RespResult doLogin(String code, String nickName, String avatarUrl) {
        return userClient.doLogin(code,nickName,avatarUrl);
    }

    @GetMapping("/rank/hk")
    public RespResult getHkRankList() {
        return userClient.getHkRankList();
    }

    @GetMapping("/rank/us")
    public RespResult getUsRankList() {
        return userClient.getUsRankList();
    }
}