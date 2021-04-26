package com.luosico.domain;

import java.io.Serializable;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
public class Address implements Serializable {
    private Integer addressId;
    private Integer userId;
    private Integer regionId;
    private String pickUpAddress;
    private String receiveAddress;

    public Address() {
    }

    public Address(Integer userId, Integer regionId, String pickUpAddress, String receiveAddress) {
        this.userId = userId;
        this.regionId = regionId;
        this.pickUpAddress = pickUpAddress;
        this.receiveAddress = receiveAddress;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
}
