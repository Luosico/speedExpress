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
     * @param order
     * @return
     */
    public int addOrder(Order order){
        return orderMapper.addOrder(order);
    }

    /**
     * 添加快递信息
     * @param express
     * @return 主键
     */
    public Integer addExpress(Express express){
        if(expressMapper.addExpress(express)==0){
            return 0;
        }else{
            return express.getExpressId();
        }
    }

    /**
     * 查询用户所有快递订单信息
     * @param userId
     */
    public List<UserOrder>  selectUserOrder(Integer userId){
        return orderMapper.selectUserOrder(userId);
    }

    /**
     * 更新订单状态
     * @param orderId
     * @param orderStatus
     * @return
     */
    public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus){
        return orderMapper.updateOrderStatus(orderId,orderStatus) == 1;
    }

}
