package com.oki.stock.controller;

import com.oki.stock.dto.HkRankDto;
import com.oki.stock.dto.UsRankDto;
import com.oki.stock.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rank")
public class RankListController {

    @Autowired
    private RankService rankService;

    @GetMapping("/hk")
    public Map<String, Object> getHkRankList() {
        Map<String, Object> modelMap = new HashMap<>();
        List<HkRankDto> rankList = rankService.getHkRankList();
        if (rankList != null) {
            modelMap.put("success", true);
            modelMap.put("hkRankList", rankList);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @GetMapping("/us")
    public Map<String, Object> getUsRankList() {
        Map<String, Object> modelMap = new HashMap<>();
        List<UsRankDto> rankList = rankService.getUsRankList();
        if (rankList != null) {
            modelMap.put("success", true);
            modelMap.put("usRankList", rankList);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }
}
