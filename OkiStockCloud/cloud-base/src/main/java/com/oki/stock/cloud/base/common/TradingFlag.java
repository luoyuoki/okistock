package com.oki.stock.cloud.base.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TradingFlag {

    private Boolean hkTrading = true;

    private Boolean usTrading = false;

}
