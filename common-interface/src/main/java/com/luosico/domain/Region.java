package com.luosico.domain;

import java.io.Serializable;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
public class Region implements Serializable {
    private Integer regionId;
    private String regionName;

    public Region(){
    }

    public Region(Integer regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
