package com.luosico.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.luosico.handle.SmsHandle;
import com.luosico.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luo kai fa
 * @Date: 2021/2/8
 */
@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    /**
     * 获取短信验证码
     * @param phoneNumber 手机号码
     */
    @GetMapping("/smsCode")
    @SentinelResource(value = "smsCode")
    public void getSmsCode(@RequestParam String phoneNumber){
        smsService.sendSmsCode(phoneNumber);
    }
}
