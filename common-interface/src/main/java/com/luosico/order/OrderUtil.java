package com.luosico.order;

import com.luosico.domain.Express;
import com.luosico.domain.Order;

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
}
