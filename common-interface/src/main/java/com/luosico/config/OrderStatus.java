package com.luosico.config;

/**
 * 订单状态
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public enum OrderStatus {
    UN_ACCEPT_ORDER("等待接单"), ACCEPTED_ORDER("已接单"), DELIVERY_ORDER("配送中"), FINISHED_ORDER("配送完成"), CONFIRMED_ORDER("确认收货");

    private String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
