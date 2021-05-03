package com.luosico.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luosico.config.OrderStatus;
import com.luosico.config.PayStatus;
import com.luosico.domain.Address;
import com.luosico.domain.JsonStructure;
import com.luosico.domain.Region;
import com.luosico.domain.UserOrder;
import com.luosico.service.OrderService;
import com.luosico.service.PayService;
import com.luosico.service.UserService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/17
 * <p>
 * 通用控制器
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    UserService userService;

    @Autowired
    UtilService utilService;

    @Autowired
    PayService payService;

    @Autowired
    OrderService orderService;

    /**
     * 获取当前请求的 username
     *
     * @param request
     * @return username
     */
    @GetMapping("username")
    public JsonStructure getUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String username = userService.getUsernameByCookie(cookies);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        return new JsonStructure("ok", "", username);
    }

    @GetMapping("name")
    public JsonStructure getName(HttpServletRequest request) {
        String username = userService.getUsernameByCookie(request.getCookies());
        return new JsonStructure("ok", "", userService.getNameByUsername(username));
    }

    /**
     * 获取支持的所有区域
     */
    @GetMapping("regions")
    public JsonStructure<List<Region>> getAllRegions() {
        List<Region> regions = utilService.getAllRegion();
        return new JsonStructure<>("ok", regions);
    }

    /**
     * 增加区域
     *
     * @param regionName 区域名字
     */
    @PostMapping("region")
    public String addRegion(@RequestBody String regionName) {
        if (utilService.addRegion(regionName)) {
            return "ok";
        }
        return "false";
    }

    /**
     * 添加地址
     */
    @PostMapping("address")
    public JsonStructure addAddress(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Address address;
        if ((address = getAddress(map, request)) != null) {
            //添加地址成功
            if (userService.addAddress(address)) {
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 更新用户地址
     */
    @PutMapping("address")
    public JsonStructure updateAddress(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Address address;
        if ((address = getAddress(map, request)) != null) {
            //修改地址成功
            if (userService.updateAddress(address)) {
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 查询用户的所有地址
     *
     * @param request
     * @return
     */
    @GetMapping("address")
    public JsonStructure<List<Address>> selectAddressesByUserId(HttpServletRequest request) {
        String userId = userService.getUserIdByCookie(request.getCookies());
        List<Address> addressList = userService.selectAddressesByUserId(Integer.valueOf(userId));
        return new JsonStructure<List<Address>>("ok", addressList);
    }

    /**
     * 删除地址
     *
     * @return
     */
    @DeleteMapping("address")
    public JsonStructure deleteAddress(@RequestBody Map<String, String> map) {
        String addressId = map.get("addressId");
        if (!utilService.isEmpty(addressId)) {
            if (userService.deleteAddress(Integer.valueOf(addressId))) {
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 获取短信验证码
     */
    @GetMapping("smsCode")
    public void getSmsCode(@RequestParam("need") String need, @RequestParam(value = "phoneNumber", required = false) String phoneNumber, HttpServletRequest request) {
        if ("no".equals(need)) {
            userService.sendSmsCode(request.getCookies());
        } else {
            userService.sendSmsCode(phoneNumber);
        }
    }

    /**
     * 更新用户信息
     * 包含：username     name
     *
     * @param map
     * @return
     */
    @PutMapping("updateUserName")
    public JsonStructure updateUser(@RequestBody Map<String, String> map, HttpServletRequest request) {
        String newUsername = map.get("username");
        String name = map.get("name");
        if (!utilService.isEmpty(newUsername, name)) {
            //获取当前登录用户名
            String username = userService.getUsernameByCookie(request.getCookies());
            if (userService.updateUserName(username, newUsername, name)) {
                return new JsonStructure();
            } else {
                return new JsonStructure("fail", "提交出现错误");
            }
        } else {
            return new JsonStructure("fail", "存在内容为空");
        }
    }

    @GetMapping("phoneNumber")
    public JsonStructure<String> getPhoneNumber(HttpServletRequest request) {
        String phoneNumber = userService.getPhoneNumber(request.getCookies());
        return new JsonStructure<>("ok", "", phoneNumber);
    }

    /**
     * 更新用户手机号码
     *
     * @param map
     * @param request
     * @return
     */
    @PutMapping("updatePhoneNumber")
    public JsonStructure updatePhoneNumber(@RequestBody Map<String, String> map, HttpServletRequest request) {
        String phoneNumber = map.get("phoneNumber");
        String smsCode = map.get("smsCode");
        //验证短信验证码
        if (userService.validateSmsCode(phoneNumber, smsCode)) {
            String username = userService.getUsernameByCookie(request.getCookies());
            //更新手机号码
            if (userService.updatePhoneNumber(username, phoneNumber)) {
                return new JsonStructure();
            }
            return new JsonStructure("fail", "服务器出现错误");
        } else {
            return new JsonStructure("fail", "验证码错误");
        }
    }

    /**
     * 更改密码
     *
     * @param map
     * @param request
     * @return
     */
    @PutMapping("updatePassword")
    public JsonStructure updatePassword(@RequestBody Map<String, String> map, HttpServletRequest request) {
        String password = map.get("password");
        String smsCode = map.get("smsCode");
        if (!utilService.isEmpty(password, smsCode)) {
            //校验验证码
            if (userService.validateSmsCode(request.getCookies(), smsCode)) {
                String username = userService.getUsernameByCookie(request.getCookies());
                //更新密码成功
                if (userService.updatePassword(username, password)) {
                    return new JsonStructure();
                } else {
                    return new JsonStructure("fail", "服务器出现错误");
                }
            } else {
                return new JsonStructure("fail", "验证码错误");
            }
        }
        return new JsonStructure("fail", "内容不能为空");
    }

    /**
     * 提交订单
     *
     * @return
     */
    @PostMapping("order")
    public JsonStructure addOrder(@RequestBody Map map, HttpServletRequest request) {
        String userId = userService.getUserIdByCookie(request.getCookies());
        Map payResult = payService.processPay(map);
        //支付成功
        if (payResult.get("result") == PayStatus.ALREADY_PAY) {
            Integer payId = (Integer) payResult.get("payId");
            map.put("payId", payId);
            map.put("userId", Integer.valueOf(userId));
            if (orderService.addOrder(map)) {
                return new JsonStructure();
            } else {
                return new JsonStructure("fail", "订单创建失败");
            }
        } else {
            return new JsonStructure("fail", "支付失败" + (String) payResult.get("message"));
        }
    }

    /**
     * 查询用户所有快递订单信息
     *
     * @param request
     * @return
     */
    @GetMapping("order")
    public JsonStructure<List<UserOrder>> selectOrder(HttpServletRequest request) {
        String userId = userService.getUserIdByCookie(request.getCookies());
        List<UserOrder> userOrder = orderService.selectUserOrder(Integer.valueOf(userId));
        if (userOrder != null) {
            return new JsonStructure<>("ok", "查询成功", userOrder);
        }
        return new JsonStructure<>("fail", "订单查询失败，服务器出现错误");
    }

    /**
     * 确认收到快递
     *
     * @param map
     * @return
     */
    @PutMapping("orderConfirmReceived")
    @Transactional
    public JsonStructure confirmReceived(@RequestBody Map<String, Integer> map) {
        Integer orderId = map.get("orderId");
        if (orderId !=null ) {
            if (orderService.orderConfirmReceived(Integer.valueOf(orderId))) {
                //将酬金付给快取员
                userService.courierFinishedExpress(orderId);
                return new JsonStructure();
            } else {
                return new JsonStructure("fai", "提交失败，服务器出现错误");
            }
        }
        return new JsonStructure("fail", "内容不能为空");
    }

    /**
     * 查询指定状态数量
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("countOrderByStatus")
    public JsonStructure countOrderByStatus(@RequestBody Map<String, List<String>> map, HttpServletRequest request) {
        Integer userId = Integer.valueOf(userService.getUserIdByCookie(request.getCookies()));
        List<OrderStatus> orderStatuses = utilService.parseOrderStatus(map.get("types"));
        if (orderStatuses != null) {
            int count = orderService.countOrderByStatus(userId, orderStatuses);
            return new JsonStructure("ok", "query success", count);
        }
        return new JsonStructure("fail", "请求内容为空");
    }

    /**
     * 查找指定状态的订单
     *
     * @return
     */
    @PostMapping("selectOrderByStatus")
    public JsonStructure selectOrderByStatus(@RequestBody Map<String, List<String>> map) {
        List<OrderStatus> orderStatusList = utilService.parseOrderStatus(map.get("types"));
        if (orderStatusList != null && orderStatusList.size() > 0) {
            List<UserOrder> orderList = orderService.selectOrderByStatus(orderStatusList);
            //清除不需要的信息
            for (UserOrder order : orderList) {
                order.setName(null);
                order.setExpressNumber(null);
                order.setExpressCompany(null);
                order.setExpressCode(null);
                order.setPhoneNumber(null);
                order.setPayId(null);
                order.setOrderStatus(null);
                order.setRemark(null);
            }
            return new JsonStructure("ok", "query success", orderList);
        }
        return new JsonStructure("fail", "请求内容为空");
    }


    /**
     * 获取 address信息
     * 存入 Address对象
     *
     * @return true 成功； false 失败或数据有错误
     */
    private Address getAddress(Map<String, String> map, HttpServletRequest request) {
        String username = userService.getUsernameByCookie(request.getCookies());
        String addressId = map.get("addressId");
        String regionId = map.get("regionId");
        String pickUpAddress = map.get("pickUpAddress");
        String receiveAddress = map.get("receiveAddress");
        if (!utilService.isEmpty(username, regionId, pickUpAddress, receiveAddress)) {
            String userId = userService.selectUserIdByUsername(username);
            Address address = new Address(Integer.valueOf(userId), Integer.valueOf(regionId), pickUpAddress, receiveAddress);
            if (!utilService.isEmpty(addressId)) {
                address.setAddressId(Integer.valueOf(addressId));
            }
            return address;
        }
        return null;
    }

}
