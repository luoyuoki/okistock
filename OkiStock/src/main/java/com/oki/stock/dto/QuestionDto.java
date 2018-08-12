package com.oki.stock.dto;

import java.io.Serializable;

public class QuestionDto implements Serializable {

    private String title;

    private String answer;

    public QuestionDto() {
    }

    public QuestionDto(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
