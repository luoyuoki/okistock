package com.oki.stock.cloud.base.vo;

import com.oki.stock.cloud.base.entity.Stock;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockDetailVO implements Serializable {

    private static final long serialVersionUID = -5871359702905326051L;

    private Stock stock;

    private Integer mostSell;

    private Integer mostBuy;
}
