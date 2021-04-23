package com.luosico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/18
 * 
 * 快取员控制器
 */

@Controller
@RequestMapping("/courier")
public class CourierController {
    @GetMapping("main")
    public String toMain(){
        return "courier/main";
    }

    @GetMapping("userInfo")
    public String toUserInfo(){
        return "courier/userInfo";
    }

    @GetMapping("addOrder")
    public String toAddOrder(){
        return "courier/addOrder";
    }

    @GetMapping("order")
    public String toOrder(){
        return "courier/order";
    }

    @GetMapping("address")
    public String toAddress(){
        return "courier/address";
    }

    @GetMapping("feedback")
    public String toFeedback(){
        return "courier/feedback";
    }

    @GetMapping("wallet")
    public String toWallet(){
        return "courier/wallet";
    }

    @GetMapping("publishedOrder")
    public String toPublishedOrder(){
        return "courier/publishedOrder";
    }
}
