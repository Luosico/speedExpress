package com.luosico.config.schedule;

import com.luosico.config.OrderStatus;
import com.luosico.domain.UserOrder;
import com.luosico.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 订单定时任务
 *
 * @Author: luo kai fa
 * @Date: 2021/5/1
 */

@Component
public class OrderSchedule {

    @Autowired
    OrderService orderService;

    private static final ArrayList<OrderStatus> list;
    private final ConcurrentHashMap<Integer, UserOrder> map = new ConcurrentHashMap<>();
    static {
        list = new ArrayList<>();
        list.add(OrderStatus.UN_ACCEPT_ORDER);
    }


    /**
     * 每隔10秒查询一次数据库，更新未被接单的订单
     */
    @Scheduled(fixedDelay = 10000)
    private void updateUnAcceptOrder(){
        List<UserOrder> userOrders = orderService.selectOrderByStatus(list);
        //clear map
        map.clear();
        for (UserOrder userOrder : userOrders){
            map.put(userOrder.getOrderId(), userOrder);
        }
    }


    /**
     * 查询订单能否被接单
     * @param orderId
     * @return
     */
    public boolean orderCanAccept(Integer orderId){
        if(map.containsKey(orderId)){
            map.remove(orderId);
            return true;
        }
        return false;
    }

    /**
     * 获取未被接单的订单信息
     * @return
     */
    public List<UserOrder> getUnAcceptOrder(){
        Iterator<UserOrder> iterator = map.values().iterator();
        List<UserOrder> userOrders = new ArrayList<>();
        while (iterator.hasNext()){
            userOrders.add(iterator.next());
        }
        return userOrders;
    }
}
