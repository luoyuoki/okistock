package com.oki.stock.cloud.base.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 6227989898587137179L;

    private String openid;
}
