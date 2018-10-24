package com.oki.stock.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {

    private String title;

    private String answer;

}
