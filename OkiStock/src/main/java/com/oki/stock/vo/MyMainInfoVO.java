package com.oki.stock.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oki.stock.common.TradingFlag;
import com.oki.stock.dto.HolderDTO;
import com.oki.stock.dto.UserDTO;
import com.oki.stock.entity.Notification;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyMainInfoVO implements Serializable {

    private static final long serialVersionUID = 4779637365435931917L;

    private UserDTO user;

    private List<HolderDTO> holderList;

    private Notification notification;

    private TradingFlag tradingFlag;

}
