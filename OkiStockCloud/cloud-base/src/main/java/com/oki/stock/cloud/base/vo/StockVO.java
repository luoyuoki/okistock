package com.oki.stock.cloud.base.vo;

import com.oki.stock.cloud.base.dto.StockDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockVO implements Serializable {

    private static final long serialVersionUID = -8032167203384800952L;

    private List<StockDTO> stockList;

    private Boolean trading = false;
}
