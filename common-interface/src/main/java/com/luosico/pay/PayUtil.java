package com.luosico.pay;

import com.luosico.config.PayStatus;

import java.util.Map;

/**
 * 支付服务RPC调用接口
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public interface PayUtil {

    /**
     * 执行支付，并返回支付结果
     * @param map
     * @return
     */
    Map processPay(Map map);

    /**
     * 查找支付订单状态
     * @param payId
     * @return
     */
    PayStatus selectPayStatus(Integer payId);
}
