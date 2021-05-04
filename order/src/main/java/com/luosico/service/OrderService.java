package com.luosico.service;

import com.luosico.config.OrderStatus;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;
import com.luosico.mapper.ExpressMapper;
import com.luosico.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ExpressMapper expressMapper;

    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    /**
     * 添加快递信息
     *
     * @param express
     * @return 主键
     */
    public Integer addExpress(Express express) {
        if (expressMapper.addExpress(express) == 0) {
            return 0;
        } else {
            return express.getExpressId();
        }
    }

    /**
     * 查询用户所有快递订单信息
     *
     * @param userId
     */
    public List<UserOrder> selectUserOrder(Integer userId) {
        return orderMapper.selectUserOrder(userId);
    }

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        return orderMapper.updateOrderStatus(orderId, orderStatus) == 1;
    }

    /**
     * 统计指定状态的订单数量
     *
     * @return
     */
    public int countOrderByStatus(Integer userId, List<OrderStatus> orderStatusList) {
        return orderMapper.countOrderByStatus(userId, orderStatusList);
    }

    /**
     * 统计指定状态的订单数量
     * @return
     */
    Integer countOrderByStatus(List<OrderStatus> orderStatusList){
        return orderMapper.countAllOrderByStatus(orderStatusList);
    }

    /**
     * 通过订单状态查找订单
     *
     * @param orderStatusList 订单状态
     */
    public List<UserOrder> selectOrderByStatus(List<OrderStatus> orderStatusList) {
        return orderMapper.selectOrderByStatus(orderStatusList);
    }

    /**
     * 查找快取员的订单
     *
     * @param courierId
     * @param orderStatusList
     * @return
     */
    public List<UserOrder> selectCourierOrder(Integer courierId, List<OrderStatus> orderStatusList) {
        return orderMapper.selectCourierOrder(courierId, orderStatusList);
    }

    public boolean updateOrder(Order order) {
        return orderMapper.updateOrder(order) == 1;
    }

    /**
     * 通过订单状态统计快取员订单数量
     *
     * @param courierId
     * @param orderStatus
     * @return
     */
    public Integer countCourierOrderByStatus(Integer courierId, OrderStatus orderStatus){
        return orderMapper.countCourierOrderByStatus(courierId, orderStatus);
    }

    /**
     * 查询订单信息
     * @param order
     * @return
     */
    public Order selectOrder(Order order){
        return orderMapper.selectOrder(order);
    }

    /**
     * 查询快递信息
     * @param expressId
     * @return
     */
    public Express selectExpress(Integer expressId){
        return expressMapper.selectExpress(expressId);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    public boolean deleteOrder(Integer orderId){
        return orderMapper.deleteOrder(orderId) == 1;
    }
}
