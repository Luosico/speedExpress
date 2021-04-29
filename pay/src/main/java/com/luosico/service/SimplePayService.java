package com.luosico.service;

import com.luosico.config.PayStatus;
import com.luosico.domain.PayService;
import com.luosico.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 简单模拟支付过程
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Component
public class SimplePayService implements PayService {

    @Autowired
    MapperService mapper;

    @Override
    public Integer createPayOrder(Long fee) {
        return mapper.addPay(fee);
    }

    @Override
    public boolean paySuccess(Integer payId) {
        return mapper.updatePayStatus(payId);
    }

    @Override
    public PayStatus getPayResult(Integer payId) {
        return mapper.selectPay(payId).getPayStatus();
    }
}
