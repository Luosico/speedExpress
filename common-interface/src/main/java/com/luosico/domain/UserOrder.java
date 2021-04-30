package com.luosico.domain;

import com.luosico.config.ExpressType;
import com.luosico.config.OrderStatus;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 用户快递订单信息
 *
 * @Author: luo kai fa
 * @Date: 2021/4/30
 */
public class UserOrder implements Serializable {
    private Integer orderId;
    private String regionName;
    //取件地址
    private String pickUpAddress;
    //收件地址
    private String receiveAddress;
    private String name;
    private String expressNumber;
    private String expressCompany;
    private String expressCode;
    private String phoneNumber;
    private ExpressType expressType;
    private String courierId;
    private String courierName;
    private String courierPhoneNumber;
    private Long fee;
    private Integer payId;
    private Timestamp createTime;
    private String createTimeString;
    private OrderStatus orderStatus;

    /**
     * 获取真实的金额
     * @return
     */
    public Long getRealFee(){
        BigInteger multiply = BigInteger.valueOf(100L);
        return BigInteger.valueOf(getFee()).divide(multiply).longValue();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ExpressType getExpressType() {
        return expressType;
    }

    public void setExpressType(ExpressType expressType) {
        this.expressType = expressType;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhoneNumber() {
        return courierPhoneNumber;
    }

    public void setCourierPhoneNumber(String courierPhoneNumber) {
        this.courierPhoneNumber = courierPhoneNumber;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        setCreateTimeString(createTime.toString());
        this.createTime = createTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }
}
