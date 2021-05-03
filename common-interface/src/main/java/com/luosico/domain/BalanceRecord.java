package com.luosico.domain;

import java.io.Serializable;

/**
 * 余额变动记录
 *
 * @Author: luo kai fa
 * @Date: 2021/5/3
 */
public class BalanceRecord implements Serializable {
    private Integer balanceId;
    private Integer orderId;
    private Integer courierId;
    private Integer amount;
    private String remark;

    public BalanceRecord(){

    }

    public BalanceRecord(Integer orderId, Integer courierId, Integer amount, String remark) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.amount = amount;
        this.remark = remark;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
