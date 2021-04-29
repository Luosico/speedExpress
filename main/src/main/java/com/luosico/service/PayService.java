package com.luosico.service;

import com.luosico.pay.PayUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 支付服务
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Service
public class PayService {
    @DubboReference
    PayUtil payUtil;

    public Map processPay(Map map){
        Map payResult = payUtil.processPay(map);
        return payResult;
    }
}
