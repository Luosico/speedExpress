package com.luosico.order;

import com.luosico.config.OrderStatus;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;

import java.util.List;

/**
 * 订单服务RPC调用接口
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
public interface OrderUtil {
    /**
     * 添加订单
     * @param order
     * @param express
     * @return
     */
    boolean addOrder(Order order, Express express);


    /**
     * 查找用户所有快递订单信息
     * @param userId
     */
    List<UserOrder> selectUserOrder(Integer userId);

    /**
     * 更新订单状态
     * @param orderId
     * @return
     */
    boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus);

    /**
     * 统计指定状态的订单数量
     * @return
     */
    int countOrderByStatus(Integer userId, List<OrderStatus> orderStatusList);

    /**
     * 通过订单状态查找订单
     * @param orderStatusList 订单状态
     */
    List<UserOrder> selectOrderByStatus(List<OrderStatus> orderStatusList);

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    boolean updateOrder(Order order);
}
