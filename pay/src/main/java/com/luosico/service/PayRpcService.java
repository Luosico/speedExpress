package com.luosico.service;

import com.luosico.config.PayStatus;
import com.luosico.domain.PayService;
import com.luosico.pay.PayUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付的RPC服务
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@DubboService
@Component
public class PayRpcService implements PayUtil {

    @Autowired
    @Qualifier("simplePayService")
    private PayService payService;

    /**
     * 执行支付
     *
     * @param map 外部支付接口信息：微信/支付宝 等
     * @return
     */
    @Override
    public Map processPay(Map map) {
        Long fee = Long.valueOf((String) map.get("fee"));

        Integer payId = payService.createPayOrder(fee);
        /* ...... */
        //调用外部支付接口，返回支付成功结果

        //如果成功，更新支付结果
        payService.paySuccess(payId);
        /* ...... */
        PayStatus result = payService.getPayResult(payId);
        HashMap response = new HashMap();
        response.put("payId", payId);
        response.put("result", result);
        response.put("message", "");

        return response;
    }

    /**
     * 查找支付订单状态
     * @param payId
     * @return
     */
    @Override
    public PayStatus selectPayStatus(Integer payId){
        return payService.getPayResult(payId);
    }
}
