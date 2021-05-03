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

    /**
     * 通过订单状态统计订单数量
     * @param userId
     * @param orderStatusList
     * @return
     */
    int countOrderByStatus(@Param("userId") Integer userId, @Param("list") List<OrderStatus> orderStatusList);

    /**
     * 通过订单状态查找订单
     * @param orderStatusList 订单状态
     */
    List<UserOrder> selectOrderByStatus(List<OrderStatus> orderStatusList);

    /**
     * 查找快取员的订单
     *
     * @param courierId
     * @param orderStatusList
     * @return
     */
    List<UserOrder> selectCourierOrder(@Param("courierId") Integer courierId, @Param("list") List<OrderStatus> orderStatusList);

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 通过订单状态统计快取员订单数量
     *
     * @param courierId
     * @param orderStatus
     * @return
     */
    Integer countCourierOrderByStatus(@Param("courierId") Integer courierId, @Param("orderStatus") OrderStatus orderStatus);
}
