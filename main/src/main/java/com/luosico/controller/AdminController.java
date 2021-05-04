package com.luosico.controller;

import com.luosico.config.OrderStatus;
import com.luosico.domain.JsonStructure;
import com.luosico.service.OrderService;
import com.luosico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/22
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("main")
    public String toMain() {
        return "admin/main";
    }

    @GetMapping("userInfo")
    public String userInfo() {
        return "admin/userInfo";
    }

    @GetMapping("unAcceptOrder")
    public String unAcceptOrder() {
        return "admin/unAcceptOrder";
    }

    @GetMapping("acceptOrder")
    public String acceptOrder() {
        return "admin/acceptOrder";
    }

    @GetMapping("deliveryOrder")
    public String deliveryOrder() {
        return "admin/deliveryOrder";
    }

    @GetMapping("confirmOrder")
    public String waitConfirmOrder() {
        return "admin/confirmOrder";
    }

    @GetMapping("finishedOrder")
    public String finishedOrder() {
        return "admin/finishedOrder";
    }

    /**
     * 查询所有快递订单的金额总和
     *
     * @return
     */
    @GetMapping("selectTotalBalance")
    @ResponseBody
    public JsonStructure selectTotalBalance() {
        Integer totalBalance = userService.selectTotalBalance();
        if (totalBalance != null) {
            return new JsonStructure("ok", "query success", totalBalance);
        }
        return new JsonStructure("fail", "查询数据失败，服务器出现错误");
    }

    @GetMapping("countTotalOrder")
    @ResponseBody
    public JsonStructure<Map<String, Integer>> countTotalOrder() {
        Map<String, Integer> map = new HashMap<>();
        map.put(OrderStatus.UN_ACCEPT_ORDER.toString(), orderService.countOrderByStatus(OrderStatus.UN_ACCEPT_ORDER));
        map.put(OrderStatus.ACCEPTED_ORDER.toString(), orderService.countOrderByStatus(OrderStatus.ACCEPTED_ORDER));
        map.put(OrderStatus.DELIVERY_ORDER.toString(), orderService.countOrderByStatus(OrderStatus.DELIVERY_ORDER));
        map.put(OrderStatus.FINISHED_ORDER.toString(), orderService.countOrderByStatus(OrderStatus.FINISHED_ORDER) + orderService.countOrderByStatus(OrderStatus.CONFIRMED_ORDER));

        return new JsonStructure<>("ok", "query success", map);
    }

    @DeleteMapping("order")
    @ResponseBody
    public JsonStructure deleteOrder(@RequestBody Map<String, Integer> map) {
        Integer orderId = map.get("orderId");
        if (orderId != null) {
            if(orderService.deleteOrder(orderId)){
                return new JsonStructure();
            }else{
                return new JsonStructure("fail","删除失败，服务器出现错误");
            }
        } else {
            return new JsonStructure("fail", "内容不能为空");
        }
    }
}
