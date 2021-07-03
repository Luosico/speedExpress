package com.luosico.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.luosico.config.OrderStatus;
import com.luosico.domain.BalanceRecord;
import com.luosico.domain.JsonStructure;
import com.luosico.domain.UserOrder;
import com.luosico.handle.OrderHandle;
import com.luosico.service.OrderService;
import com.luosico.service.UserService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
            return new JsonStructure("ok", "query success", orderList);
        }
        return new JsonStructure("fail", "查询失败，服务器出现问题");
    }

    /**
     * 接单
     * @param map
     * @param request
     * @return
     */
    @PutMapping("tryAcceptOrder")
    @ResponseBody
    @SentinelResource(value = "tryAcceptOrder", blockHandler = "tryAcceptOrder", blockHandlerClass = OrderHandle.class)
    public JsonStructure<String> tryAcceptOrder(@RequestBody Map<String, Integer> map, HttpServletRequest request) {
        Integer userId = userService.getUserIdByCookie(request.getCookies());
        Integer orderId = map.get("orderId");
        if (orderId != null) {
            //抢单成功
            if (orderService.tryAcceptOrder(userId, orderId)) {
                return new JsonStructure<>();
            } else {
                //抢单失败
                return new JsonStructure<>("fail", "该订单已被接单了", "");
            }
        } else {
            return new JsonStructure<>("fail", "内容不能为空", "");
        }
    }

    /**
     * 查询快取员的快取订单
     * @param map
     * @param request
     * @return
     */
    @PutMapping("selectCourierOrder")
    @ResponseBody
    public JsonStructure<List<UserOrder>> selectCourierOrder(@RequestBody Map<String, List<String>> map, HttpServletRequest request) {
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());
        List<OrderStatus> orderStatusList = utilService.parseOrderStatus(map.get("types"));

        if (orderStatusList != null && orderStatusList.size() > 0) {
            List<UserOrder> userOrderList = orderService.selectCourierOrder(courierId, orderStatusList);
            return new JsonStructure<>("ok", "query success", userOrderList);
        }
        return new JsonStructure<>("fail", "服务器出现错误");
    }

    /**
     * 快取员更新订单状态
     * @param map
     * @param httpServletRequest
     * @return
     */
    @PutMapping("order")
    @ResponseBody
    public JsonStructure updateOrderStatus(@RequestBody Map<String, String> map , HttpServletRequest httpServletRequest){
        Integer orderId = Integer.valueOf(map.get("orderId"));
        OrderStatus orderStatus = OrderStatus.valueOf(map.get("orderStatus"));
        if(orderService.updateOrderStatus(orderId, orderStatus)){
            return new JsonStructure("ok","更新订单状态成功");
        }else{
            return new JsonStructure("fail","更新订单状态失败，服务器出现错误");
        }
    }

    @GetMapping("countCourierOrder")
    @ResponseBody
    public JsonStructure<Map<String,Integer>> countCourierOrder(HttpServletRequest request){
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());

        Map<String, Integer> map = new HashMap<>();
        map.put("acceptedOrder", orderService.countCourierOrderByStatus(courierId, OrderStatus.ACCEPTED_ORDER));
        map.put("deliveryOrder", orderService.countCourierOrderByStatus(courierId, OrderStatus.DELIVERY_ORDER));
        map.put("finishedOrder", orderService.countCourierOrderByStatus(courierId, OrderStatus.FINISHED_ORDER) + orderService.countCourierOrderByStatus(courierId, OrderStatus.CONFIRMED_ORDER));

        return new JsonStructure<>("ok", "query success", map);
    }

    /**
     * 提现
     * @return
     */
    @PostMapping("getMoney")
    @ResponseBody
    public JsonStructure getMoney(@RequestBody Map<String, String> map, HttpServletRequest request){
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());
        Integer amount = Integer.valueOf(map.get("amount"));

        //提现
        if(userService.drawMoney(courierId, amount)){
            return new JsonStructure();
        }else{
            return new JsonStructure("fail","提现失败，服务器出现错误");
        }
    }

    @GetMapping("selectTotalBalance")
    @ResponseBody
    public JsonStructure<Integer> selectTotalBalance(HttpServletRequest request){
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());
        Integer total = userService.selectTotalBalance(courierId);
        return new JsonStructure<>("ok","success", total);
    }

    @GetMapping("selectBalance")
    @ResponseBody
    public JsonStructure<Integer> getBalance(HttpServletRequest request){
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());
        Integer balance = userService.selectWalletBalance(courierId);
        return new JsonStructure<>("ok","success", balance);
    }

    @GetMapping("selectBalanceRecord")
    @ResponseBody
    public JsonStructure<List<BalanceRecord>> selectBalanceRecord(HttpServletRequest request){
        Integer courierId = userService.selectCourierIdByCookies(request.getCookies());
        List<BalanceRecord> balanceRecordList = userService.selectBalanceRecord(courierId);

        return new JsonStructure<>("ok","query success", balanceRecordList);
    }

}
