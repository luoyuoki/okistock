package com.oki.stock.controller;

import com.oki.stock.common.CodeMsg;
import com.oki.stock.common.RespResult;
import com.oki.stock.common.TradingFlag;
import com.oki.stock.dto.HolderParamDTO;
import com.oki.stock.dto.StockDTO;
import com.oki.stock.entity.Holder;
import com.oki.stock.entity.Order;
import com.oki.stock.entity.Stock;
import com.oki.stock.entity.User;
import com.oki.stock.exception.StockServerException;
import com.oki.stock.rabbitmq.MQOrderSender;
import com.oki.stock.service.HolderService;
import com.oki.stock.service.StockService;
import com.oki.stock.service.UserService;
import com.oki.stock.util.SpringContextUtil;
import com.oki.stock.vo.StockDetailVO;
import com.oki.stock.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockMainController {

    @Autowired
    private StockService stockService;

    @Autowired
    private HolderService holderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MQOrderSender mqOrderSender;

    @GetMapping("/hk")
    public RespResult getHkStockList() {
        List<StockDTO> stockList = stockService.getHkStocks();
        if (stockList != null) {
            StockVO stockVO = new StockVO();
            stockVO.setStockList(stockList);
            stockVO.setTrading(SpringContextUtil.getBean(TradingFlag.class).getHkTrading());
            return RespResult.bySuccess(stockVO);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping("/us")
    public RespResult getUsStockList() {
        List<StockDTO> stockList = stockService.getUsStocks();
        if (stockList != null) {
            StockVO stockVO = new StockVO();
            stockVO.setStockList(stockList);
            stockVO.setTrading(SpringContextUtil.getBean(TradingFlag.class).getUsTrading());
            return RespResult.bySuccess(stockVO);
        } else {
            return RespResult.byError();
        }
    }

    @GetMapping(value = "/detail")
    public RespResult getStockDetail(@RequestParam("stockId") Integer stockId, @RequestParam("openid") String openid) {
        Stock stock = stockService.getStockById(stockId);
        if (stock != null) {
            HolderParamDTO holderParamDTO = new HolderParamDTO();
            holderParamDTO.setOpenid(openid);
            holderParamDTO.setStockId(stockId);
            Integer mostSell = 0;
            Integer mostBuy = 0;
            Holder holder = holderService.getUserHolder(holderParamDTO);
            if (holder != null) {
                mostSell = holder.getStockNums();
            }

            String stockScope = stock.getStockScope();
            BigDecimal roundNums = new BigDecimal(stock.getRoundNums());
            User user = userService.getUserByOpenid(openid);
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

    @PostMapping(value = "/order")
    public RespResult addNewOrder(@RequestBody Order order) {
        if (order == null || StringUtils.isEmpty(order.getOpenid())) {
            throw new StockServerException(CodeMsg.ADD_ORDER_INFO_EMPTY);
        }

        mqOrderSender.send(order);
        return RespResult.bySuccess();
    }

}
