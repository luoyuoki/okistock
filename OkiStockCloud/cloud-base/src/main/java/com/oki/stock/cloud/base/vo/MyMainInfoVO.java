package com.oki.stock.cloud.base.vo;

import com.oki.stock.cloud.base.common.TradingFlag;
import com.oki.stock.cloud.base.dto.UserDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyMainInfoVO implements Serializable {

    private static final long serialVersionUID = 4779637365435931917L;

    private UserDTO user;

    private TradingFlag tradingFlag;

}
