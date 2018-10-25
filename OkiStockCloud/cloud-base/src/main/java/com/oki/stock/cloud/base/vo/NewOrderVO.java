package com.oki.stock.cloud.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oki.stock.cloud.base.dto.NewOrderDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewOrderVO implements Serializable {

    private static final long serialVersionUID = 7643073774549485909L;

    private List<NewOrderDTO> newOrderList;

    private String hkRestDollar;

    private String hkFrozenCapital;

    private String usRestDollar;

    private String usFrozenCapital;
}
