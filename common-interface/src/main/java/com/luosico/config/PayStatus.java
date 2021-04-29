package com.luosico.config;

/**
 * 支付状态
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public enum PayStatus {
    WAIT_PAY("未支付"), ALREADY_PAY("已支付");
    private String status;

    PayStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
