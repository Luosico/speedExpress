package com.luosico.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 快取员信息
 * @Author: luo kai fa
 * @Date: 2021/4/30
 */
public class Courier implements Serializable {
    private Integer courierId;
    private Integer userId;
    private String identityId;
    private Timestamp createTime;

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
