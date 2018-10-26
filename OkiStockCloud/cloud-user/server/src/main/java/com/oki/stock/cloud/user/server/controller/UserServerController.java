package com.oki.stock.cloud.user.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.oki.stock.cloud.base.common.CodeMsg;
import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.base.common.TradingFlag;
import com.oki.stock.cloud.base.dto.HkRankDTO;
import com.oki.stock.cloud.base.dto.HolderDTO;
import com.oki.stock.cloud.base.dto.UsRankDTO;
import com.oki.stock.cloud.base.dto.UserDTO;
import com.oki.stock.cloud.base.entity.User;
import com.oki.stock.cloud.base.vo.LoginVO;
import com.oki.stock.cloud.base.vo.MyMainInfoVO;
import com.oki.stock.cloud.order.client.OrderClient;
import com.oki.stock.cloud.user.server.service.RankService;
import com.oki.stock.cloud.user.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserServerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RankService rankService;

    @Autowired
    private OrderClient orderClient;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${user.assets.hk}")
    private String hkAssets;

    @Value("${user.assets.us}")
    private String usAssets;

    @GetMapping("/my")
    public RespResult getMyMainInfo(String openid) {
        UserDTO user = userService.getUserInfoByOpenid(openid);
        if (user != null) {
            MyMainInfoVO infoVO = new MyMainInfoVO();
            infoVO.setUser(user);

            List<HolderDTO> holderList = orderClient.getHolderList(openid);
            infoVO.setHolderList(holderList);
            // TODO
//            infoVO.setTradingFlag(SpringContextUtil.getBean(TradingFlag.class));
            return RespResult.bySuccess(infoVO);
        } else {
            return RespResult.byError(CodeMsg.USER_NOT_EXIST);
        }
    }

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

    @GetMapping("/rank/hk")
    public RespResult getHkRankList() {
        List<HkRankDTO> rankList = rankService.getHkRankList();
        if (rankList != null) {
            return RespResult.bySuccess(rankList);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping("/rank/us")
    public RespResult getUsRankList() {
        List<UsRankDTO> rankList = rankService.getUsRankList();
        if (rankList != null) {
            return RespResult.bySuccess(rankList);
        } else {
            return RespResult.byError();
        }
    }

    @PostMapping("/get")
    public User getUserByOpenid(@RequestParam("openid") String openid) {
        return userService.getUserByOpenid(openid);
    }


    @PostMapping("/getInfo")
    public UserDTO getUserInfoByOpenid(@RequestParam("openid") String openid) {
        return userService.getUserInfoByOpenid(openid);
    }

}
