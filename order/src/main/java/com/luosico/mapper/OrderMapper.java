package com.luosico.mapper;

import com.luosico.config.OrderStatus;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询用户所有快递订单信息
     * @param orderId
     * @return
     */
    List<UserOrder> selectUserOrder(Integer orderId);


    /**
     * 更新订单状态
     * @param orderId
     * @param orderStatus
     * @return
     */
    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("orderStatus") OrderStatus orderStatus);
}
