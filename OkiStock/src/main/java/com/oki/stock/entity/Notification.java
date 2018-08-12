package com.oki.stock.entity;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    private Integer noId;

    private String title;

    private String message;

    //0 无效，1 有效
    private String enabled;

    private Date updateTime;

    public Notification() {
    }

    public Notification(Integer noId, String title, String message, String enabled, Date updateTime) {
        this.noId = noId;
        this.title = title;
        this.message = message;
        this.enabled = enabled;
        this.updateTime = updateTime;
    }

    public Integer getNoId() {
        return noId;
    }

    public void setNoId(Integer noId) {
        this.noId = noId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "noId=" + noId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", enabled='" + enabled + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
