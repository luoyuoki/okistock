package com.oki.stock.entity;

import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class TradingFlag implements Serializable {

    private Boolean hkTrading;

    private Boolean usTrading;

    public Boolean getHkTrading() {
        return hkTrading;
    }

    public void setHkTrading(Boolean hkTrading) {
        this.hkTrading = hkTrading;
    }

    public Boolean getUsTrading() {
        return usTrading;
    }

    public void setUsTrading(Boolean usTrading) {
        this.usTrading = usTrading;
    }
}
