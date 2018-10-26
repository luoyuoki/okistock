package com.oki.stock.cloud.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oki.stock.cloud.base.common.TradingFlag;
import com.oki.stock.cloud.base.dto.HolderDTO;
import com.oki.stock.cloud.base.dto.UserDTO;
import com.oki.stock.cloud.base.entity.Notification;
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
