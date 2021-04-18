package com.luosico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户功能控制器
 *
 * @Author: luo kai fa
 * @Date: 2021/3/26
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("main")
    public String toMain(){
        return "user/main";
    }

    @GetMapping("userInfo")
    public String toUserInfo(){
        return "user/userInfo";
    }

    @GetMapping("addOrder")
    public String toAddOrder(){
        return "user/addOrder";
    }

    @GetMapping("order")
    public String toOrder(){
        return "user/order";
    }

    @GetMapping("address")
    public String toAddress(){
        return "user/address";
    }

    @GetMapping("feedback")
    public String toFeedback(){
        return "user/feedback";
    }

    @GetMapping("becomeSender")
    public String toBecomeSender(){
        return "user/becomeSender";
    }
}
