package com.oki.stock.cloud.user.client;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.base.dto.UserDTO;
import com.oki.stock.cloud.base.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@Component
@FeignClient(name = "user-server")
public interface UserClient {

    @GetMapping("/user/my")
    RespResult getMyMainInfo(@RequestParam("openid") String openid);

    @GetMapping("/user/login")
    RespResult doLogin(@RequestParam("code") String code,@RequestParam("nickName") String nickName,@RequestParam("avatarUrl") String avatarUrl);

    @GetMapping("/user/rank/hk")
    RespResult getHkRankList();

    @GetMapping("/user/rank/us")
    RespResult getUsRankList();

    @PostMapping("/user/get")
    User getUserByOpenid(@RequestParam("openid") String openid);

    @PostMapping("/user/getInfo")
    UserDTO getUserInfoByOpenid(@RequestParam("openid") String openid);
}
