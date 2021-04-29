package com.luosico.service;

import com.luosico.config.PayStatus;
import com.luosico.domain.Pay;
import com.luosico.mapper.PayMapper;
import org.apache.dubbo.common.extension.Activate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Service
public class MapperService {

    @Autowired
    PayMapper payMapper;

    /**
     * 新增pay订单
     *
     * @param fee
     * @return 插入记录的自增id值
     */
    public Integer addPay(Long fee) {
        Pay pay = new Pay(fee);
        if (payMapper.addPay(pay) == 0) {
            return 0;
        }
        return pay.getPayId();
    }

    public Pay selectPay(Integer payId) {
        return payMapper.selectPay(payId);
    }

    public boolean updatePayStatus(Integer payId) {
        return 1 == payMapper.updatePayStatus(payId, PayStatus.ALREADY_PAY);
    }
}
