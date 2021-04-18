package com.luosico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/17
 *
 * 通用控制器
 */
@Controller
public class CommonController {

    @GetMapping("/feedback")
    public String feedback(){
        return "common/feedback";
    }
}
