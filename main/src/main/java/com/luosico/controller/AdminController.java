package com.luosico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/22
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

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

    @GetMapping("waitConfirmOrder")
    public String waitConfirmOrder() {
        return "admin/waitConfirmOrder";
    }

    @GetMapping("finishedOrder")
    public String finishedOrder() {
        return "admin/finishedOrder";
    }
}
