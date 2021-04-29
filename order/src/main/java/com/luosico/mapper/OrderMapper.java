package com.luosico.mapper;

import com.luosico.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Mapper
public interface OrderMapper {

    /**
     * 新增订单
     * @param order
     * @return
     */
    int addOrder(Order order);

    /**
     * 查询订单
     * @param order 查询条件
     * @return
     */
    Order selectOrder(Order order);
}
