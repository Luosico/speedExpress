package com.luosico.service;

import com.luosico.config.OrderStatus;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;
import com.luosico.order.OrderUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Service
@DubboService
public class OrderRpcService implements OrderUtil {

    @Autowired
    OrderService orderService;

    @Override
    public boolean addOrder(Order order, Express express) {
        Integer expressId = orderService.addExpress(express);
        order.setExpressId(expressId);
        return orderService.addOrder(order) != 0;
    }

    @Override
    public List<UserOrder> selectUserOrder(Integer userId) {
        return orderService.selectUserOrder(userId);
    }

    @Override
    public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        return orderService.updateOrderStatus(orderId,orderStatus);
    }

    @Override
    public int countOrderByStatus(Integer userId, List<OrderStatus> orderStatusList) {
        return orderService.countOrderByStatus(userId, orderStatusList);
    }
}
