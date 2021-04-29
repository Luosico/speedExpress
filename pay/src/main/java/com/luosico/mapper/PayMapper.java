package com.luosico.mapper;

import com.luosico.config.PayStatus;
import com.luosico.domain.Pay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Mapper
public interface PayMapper {

    /**
     * 新增pay订单
     * @param pay
     * @return
     */
    Integer addPay(Pay pay);

    /**
     * 查找pay订单
     * @param payId
     * @return
     */
    Pay selectPay(@Param("payId") Integer payId);

    /**
     * 更新支付订单状态为已支付
     * @param payStatus
     * @return
     */
    int updatePayStatus(@Param("payId") Integer payId, @Param("payStatus") PayStatus payStatus);
}
