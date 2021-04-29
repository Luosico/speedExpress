package com.luosico.config;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public enum ExpressType {
    SMALL("小"),MEDIUM("中"),LARGE("大");

    private String type;

    ExpressType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
