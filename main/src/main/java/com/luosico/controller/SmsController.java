package com.luosico.controller;

import com.luosico.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: luo kai fa
 * @Date: 2021/2/8
 */
@Controller
public class SmsController {

    @Autowired
    SmsService smsService;

    /**
     * 获取短信验证码
     * @param phoneNumber 手机号码
     * @param type 对象类型: login/register
     */
    @GetMapping("/smsCode")
    @ResponseBody
    public void getSmsCode(@RequestParam String phoneNumber, @RequestParam String type){
        smsService.sendSmsCode(phoneNumber);
    }
}
