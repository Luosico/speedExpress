package com.luosico.service;

import com.luosico.config.ExpressType;
import com.luosico.config.OrderStatus;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.domain.UserOrder;
import com.luosico.order.OrderUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

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
        express.setFee(Long.valueOf((String) map.get("fee")));
        express.setRemark((String) map.get("remark"));

        Order order = new Order();
        order.setUserId((Integer) map.get("userId"));
        order.setPayId((Integer) map.get("payId"));
        order.setOrderStatus(OrderStatus.UN_ACCEPT_ORDER);

        return orderUtil.addOrder(order, express);
    }

    /**
     * 查询用户所有快递订单信息
     * @param userId
     */
    public List<UserOrder> selectUserOrder(Integer userId){
        List<UserOrder> userOrders = orderUtil.selectUserOrder(userId);
        for (UserOrder userOrder: userOrders){
            userOrder.setFee(userOrder.getRealFee());
        }
        return userOrders;
    }

    /**
     * 确认收到快递
     * @return
     */
    public boolean orderConfirmReceived(Integer orderId){
        return orderUtil.updateOrderStatus(orderId, OrderStatus.CONFIRMED_ORDER);
    }
}
