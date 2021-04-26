package com.luosico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Author: luo kai fa
 * @Date: 2021/1/11
 */

@Controller
public class LoginController {

    @GetMapping("/")
    public String toLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "common/register";
    }

    /**
     * 忘记密码
     */
    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        return "common/forgetPassword";
    }

}
