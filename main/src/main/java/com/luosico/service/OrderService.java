package com.luosico.service;

import com.luosico.config.ExpressType;
import com.luosico.config.OrderStatus;
import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.order.OrderUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

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
        express.setFee((Long) map.get("fee"));
        express.setRemark((String) map.get("remark"));

        Order order = new Order();
        order.setUserId((Integer) map.get("userId"));
        order.setPayId((Integer) map.get("payId"));
        order.setOrderStatus(OrderStatus.valueOf((String) map.get("orderStatus")));

        return orderUtil.addOrder(order, express);
    }
}
