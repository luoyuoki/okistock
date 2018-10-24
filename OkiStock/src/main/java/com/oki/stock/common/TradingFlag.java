package com.oki.stock.common;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
@Data
public class TradingFlag {

    private Boolean hkTrading = true;

    private Boolean usTrading = false;

}
