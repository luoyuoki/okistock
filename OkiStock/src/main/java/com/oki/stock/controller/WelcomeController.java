package com.oki.stock.controller;

import com.alibaba.fastjson.JSONObject;
import com.oki.stock.common.RespResult;
import com.oki.stock.entity.User;
import com.oki.stock.service.UserService;
import com.oki.stock.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@Slf4j
public class WelcomeController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${user.assets.hk}")
    private String hkAssets;

    @Value("${user.assets.us}")
    private String usAssets;

    @GetMapping("/login")
    public RespResult doLogin(String code, String nickName, String avatarUrl) {
        String wechatUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String url = wechatUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=authorization_code";

        String result = restTemplate.getForObject(url, String.class);
        JSONObject resultJson = JSONObject.parseObject(result);
//        String session_key = resultJson.getString("session_key");
        String openid = resultJson.getString("openid");

        log.info("nickName:{}", nickName);
//        log.info("avatarUrl:{}", avatarUrl);

        User user = new User();
        user.setOpenid(openid);
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setHkAssets(new BigDecimal(hkAssets));
        user.setHkRestDollar(new BigDecimal(hkAssets));
        user.setHkProfitAmount(new BigDecimal(0));
        user.setHkProfitPercent("+0.00%");
        user.setHkFrozenCapital(new BigDecimal(0));
        user.setUsAssets(new BigDecimal(usAssets));
        user.setUsRestDollar(new BigDecimal(usAssets));
        user.setUsProfitAmount(new BigDecimal(0));
        user.setUsFrozenCapital(new BigDecimal(0));
        user.setUsProfitPercent("+0.00%");

        if (userService.addUser(user)) {
            LoginVO loginVO = new LoginVO();
            loginVO.setOpenid(code);
            return RespResult.bySuccess(openid);
        } else {
            return RespResult.byError();
        }
    }

}
