package com.oki.stock.cloud.stock.server.controller;

import com.oki.stock.cloud.base.common.RespResult;
import com.oki.stock.cloud.base.common.TradingFlag;
import com.oki.stock.cloud.base.dto.HolderParamDTO;
import com.oki.stock.cloud.base.dto.StockDTO;
import com.oki.stock.cloud.base.entity.Holder;
import com.oki.stock.cloud.base.entity.Stock;
import com.oki.stock.cloud.base.entity.User;
import com.oki.stock.cloud.base.vo.StockDetailVO;
import com.oki.stock.cloud.base.vo.StockVO;
import com.oki.stock.cloud.stock.server.service.StockService;
import com.oki.stock.cloud.user.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 0ki
 * @date 2018/10/22
 */
@RestController
@RequestMapping("/stock")
public class StockServerController {

    @Autowired
    private StockService stockService;

//    @Autowired
//    private HolderService holderService;

    @Autowired
    private UserClient userClient;

    @GetMapping("/hk")
    public RespResult getHkStockList() {
        List<StockDTO> stockList = stockService.getHkStocks();
        return getRespResult(stockList);
    }

    @GetMapping("/us")
    public RespResult getUsStockList() {
        List<StockDTO> stockList = stockService.getUsStocks();
        return getRespResult(stockList);
    }

    private RespResult getRespResult(List<StockDTO> stockList) {
        if (stockList != null) {
            StockVO stockVO = new StockVO();
            stockVO.setStockList(stockList);
            // TODO
//            stockVO.setTrading(SpringContextUtil.getBean(TradingFlag.class).getHkTrading());
            return RespResult.bySuccess(stockVO);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping("/detail")
    public RespResult getStockDetail(@RequestParam("stockId") Integer stockId, @RequestParam("openid") String openid) {
        Stock stock = stockService.getStockById(stockId);
        if (stock != null) {
            HolderParamDTO holderParamDTO = new HolderParamDTO();
            holderParamDTO.setOpenid(openid);
            holderParamDTO.setStockId(stockId);
            Integer mostSell = 0;
            Integer mostBuy = 0;
//            Holder holder = holderService.getUserHolder(holderParamDTO);
//            if (holder != null) {
//                mostSell = holder.getStockNums();
//            }

            String stockScope = stock.getStockScope();
            BigDecimal roundNums = new BigDecimal(stock.getRoundNums());
            User user = userClient.getUserByOpenid(openid);
            BigDecimal stockPrice = new BigDecimal(stock.getCurrentPrice());
            if (stockScope.equals("hk")) {
                BigDecimal hkRest = user.getHkRestDollar();
                mostBuy = hkRest.divideToIntegralValue(stockPrice).divideToIntegralValue(roundNums).multiply(roundNums).intValue();
            } else if (stockScope.equals("us")) {
                BigDecimal usRest = user.getUsRestDollar();
                mostBuy = usRest.divideToIntegralValue(stockPrice).intValue();
            }

            StockDetailVO detailVO = new StockDetailVO();
            detailVO.setStock(stock);
            detailVO.setMostBuy(mostBuy);
            detailVO.setMostSell(mostSell);

            return RespResult.bySuccess(detailVO);
        } else {
            return RespResult.byError();
        }
    }
}
