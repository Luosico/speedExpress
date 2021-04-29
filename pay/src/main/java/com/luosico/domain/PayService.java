package com.luosico.domain;

import com.luosico.config.PayStatus;

import java.util.Map;

/**
 * 支付服务
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public interface PayService {
    /**
     * 创建支付订单
     * @param fee 金额, 为原金额的100倍
     * @return payId
     */
    Integer createPayOrder(Long fee);

    /**
     * 支付成功后调用该方法
     * 将支付状态变更为成功
     * @param payId
     * @return
     */
    boolean paySuccess(Integer payId);

    /**
     * 获取支付结果
     * @param payId
     * @return
     */
    PayStatus getPayResult(Integer payId);
}
