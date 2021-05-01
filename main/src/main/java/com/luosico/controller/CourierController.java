package com.luosico.controller;

import com.luosico.domain.JsonStructure;
import com.luosico.domain.UserOrder;
import com.luosico.service.OrderService;
import com.luosico.service.UserService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/18
 * <p>
 * 快取员控制器
 */

@Controller
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    UserService userService;

    @Autowired
    UtilService utilService;

    @Autowired
    OrderService orderService;

    @GetMapping("main")
    public String toMain() {
        return "courier/main";
    }

    @GetMapping("userInfo")
    public String toUserInfo() {
        return "courier/userInfo";
    }

    @GetMapping("addOrder")
    public String toAddOrder() {
        return "courier/addOrder";
    }

    @GetMapping("order")
    public String toOrder() {
        return "courier/order";
    }

    @GetMapping("address")
    public String toAddress() {
        return "courier/address";
    }

    @GetMapping("feedback")
    public String toFeedback() {
        return "courier/feedback";
    }

    @GetMapping("wallet")
    public String toWallet() {
        return "courier/wallet";
    }

    @GetMapping("publishedOrder")
    public String toPublishedOrder() {
        return "courier/publishedOrder";
    }

    @GetMapping("getUnAcceptOrder")
    @ResponseBody
    public JsonStructure getUnAcceptOrder() {
        List<UserOrder> orderList = orderService.getUnAcceptOrder();
        if (orderList != null) {
            //清除不需要的信息
            for (UserOrder order : orderList) {
                order.setName(null);
                order.setExpressNumber(null);
                order.setExpressCompany(null);
                order.setExpressCode(null);
                order.setPhoneNumber(null);
                order.setPayId(null);
                order.setOrderStatus(null);
                //order.setRemark(null);
            }
            return new JsonStructure("ok","query success", orderList);
        }
        return new JsonStructure("fail","查询失败，服务器出现问题");
    }

    @PutMapping("tryAcceptOrder")
    @ResponseBody
    public JsonStructure<String> tryAcceptOrder(@RequestBody Map<String, Integer> map, HttpServletRequest request) {
        Integer userId = Integer.valueOf(userService.getUserIdByCookie(request.getCookies()));
        Integer orderId = map.get("orderId");
        if (orderId != null) {
            //抢单成功
            if (orderService.tryAcceptOrder(userId, orderId)) {
                return new JsonStructure<>();
            } else {
                //抢单失败
                return new JsonStructure<>("fail", "该订单已被接单了", "");
            }
        }else{
            return new JsonStructure<>("fail", "内容不能为空", "");
        }
    }
}
