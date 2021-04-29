package com.luosico.service;

import com.luosico.domain.Express;
import com.luosico.domain.Order;
import com.luosico.order.OrderUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
