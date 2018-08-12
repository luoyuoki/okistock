package com.oki.stock.controller;

import com.alibaba.fastjson.JSONObject;
import com.oki.stock.entity.User;
import com.oki.stock.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/login")
    public Map<String, Object> doLogin(String code, String nickName, String avatarUrl) {
        String wechatUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String url = wechatUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=authorization_code";

        String result = restTemplate.getForObject(url, String.class);
        JSONObject resultJson = JSONObject.parseObject(result);
//        String session_key = resultJson.getString("session_key");
        String openid = resultJson.getString("openid");

        logger.info("nickName:" + nickName);
//        logger.info("avatarUrl:" + avatarUrl);

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

        Map<String, Object> modelMap = new HashMap<>();
        if (userService.addUser(user)) {
            modelMap.put("success", true);
            modelMap.put("openid", openid);
        } else {
            modelMap.put("success", false);
        }

        return modelMap;
    }

}
