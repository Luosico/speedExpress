package com.luosico.domain;

import com.luosico.config.PayStatus;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public class Pay implements Serializable {
    private Integer payId;
    private Long fee;
    private Timestamp createTime;
    private PayStatus payStatus;

    public Pay() {
        this.payStatus = PayStatus.WAIT_PAY;
    }

    public Pay(Long fee) {
        this.payStatus = PayStatus.WAIT_PAY;
        this.fee = fee;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }
}

