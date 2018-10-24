package com.oki.stock.controller;

import com.oki.stock.common.RespResult;
import com.oki.stock.dto.HkRankDTO;
import com.oki.stock.dto.UsRankDTO;
import com.oki.stock.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankListController {

    @Autowired
    private RankService rankService;

    @GetMapping("/hk")
    public RespResult getHkRankList() {
        List<HkRankDTO> rankList = rankService.getHkRankList();
        if (rankList != null) {
            return RespResult.bySuccess(rankList);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping("/us")
    public RespResult getUsRankList() {
        List<UsRankDTO> rankList = rankService.getUsRankList();
        if (rankList != null) {
            return RespResult.bySuccess(rankList);
        } else {
            return RespResult.byError();
        }
    }
}
