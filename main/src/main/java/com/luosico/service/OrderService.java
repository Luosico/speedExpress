package com.luosico.service;

import com.luosico.config.ExpressType;
import com.luosico.config.OrderStatus;
import com.luosico.config.schedule.OrderSchedule;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;
import com.luosico.order.OrderUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单服务
 *
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Service
public class OrderService {

    @DubboReference
    OrderUtil orderUtil;

    @Autowired
    UserService userService;

    @Autowired
    OrderSchedule orderSchedule;

    /**
     * 创建订单
     *
     * @param map
     * @return
     */
    public boolean addOrder(Map map) {
        Express express = new Express();
        express.setAddressId((Integer) map.get("addressId"));
        express.setName((String) map.get("name"));
        express.setExpressNumber((String) map.get("expressNumber"));
        express.setExpressCompany((String) map.get("expressCompany"));
        express.setExpressCode((String) map.get("expressCode"));
        express.setPhoneNumber((String) map.get("phoneNumber"));
        express.setExpressType(ExpressType.valueOf((String) map.get("expressType")));
        express.setFee(Long.valueOf((Integer) map.get("fee")));
        express.setRemark((String) map.get("remark"));

        Order order = new Order();
        order.setUserId((Integer) map.get("userId"));
        order.setPayId((Integer) map.get("payId"));
        order.setOrderStatus(OrderStatus.UN_ACCEPT_ORDER);

        return orderUtil.addOrder(order, express);
    }

    /**
     * 查询用户所有快递订单信息
     *
     * @param userId
     */
    public List<UserOrder> selectUserOrder(Integer userId) {
        List<UserOrder> userOrders = orderUtil.selectUserOrder(userId);
        return userOrders;
    }

    /**
     * 确认收到快递
     *
     * @return
     */
    public boolean orderConfirmReceived(Integer orderId) {
        return orderUtil.updateOrderStatus(orderId, OrderStatus.CONFIRMED_ORDER);
    }

    /**
     * 统计指定状态的订单数量
     *
     * @return
     */
    public int countOrderByStatus(Integer userId, List<OrderStatus> orderStatusList) {
        return orderUtil.countOrderByStatus(userId, orderStatusList);
    }

    /**
     * 通过订单状态查找订单
     *
     * @param orderStatusList 订单状态
     */
    public List<UserOrder> selectOrderByStatus(List<OrderStatus> orderStatusList) {
        List<UserOrder> userOrders = orderUtil.selectOrderByStatus(orderStatusList);
        return userOrders;
    }

    /**
     * 查找快取员的订单
     *
     * @param courierId
     * @param orderStatusList
     * @return
     */
    public List<UserOrder> selectCourierOrder(Integer courierId, List<OrderStatus> orderStatusList) {
        List<UserOrder> userOrderList = orderUtil.selectCourierOrder(courierId, orderStatusList);
        return userOrderList;
    }

    /**
     * 接单
     *
     * @param userId
     * @param orderId
     */
    @Transactional
    public boolean tryAcceptOrder(Integer userId, Integer orderId) {
        Integer courierId = userService.selectCourierIdByUserId(userId);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCourierId(courierId);
        order.setOrderStatus(OrderStatus.ACCEPTED_ORDER);

        if (orderSchedule.orderCanAccept(orderId)) {
            return updateOrder(order);
        }
        return false;
    }

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        return orderUtil.updateOrderStatus(orderId, orderStatus);
    }

    /**
     * 更新订单信息
     *
     * @param order
     * @return
     */
    public boolean updateOrder(Order order) {
        return orderUtil.updateOrder(order);
    }

    /**
     * 获取未被接单的订单信息
     *
     * @return
     */
    public List<UserOrder> getUnAcceptOrder() {
        return orderSchedule.getUnAcceptOrder();
    }


    /**
     * 通过订单状态统计快取员订单数量
     *
     * @param courierId
     * @param orderStatus
     * @return
     */
    public Integer countCourierOrderByStatus(Integer courierId, OrderStatus orderStatus) {
        return orderUtil.countCourierOrderByStatus(courierId, orderStatus);
    }

    /**
     * 查询订单信息
     * @param orderId
     * @return
     */
    public Order selectOrder(Integer orderId){
        Order order = new Order();
        order.setOrderId(orderId);
        return orderUtil.selectOrder(order);
    }

    /**
     * 查询快递信息
     * @param expressId
     * @return
     */
    public Express selectExpress(Integer expressId){
        return orderUtil.selectExpress(expressId);
    }
}
