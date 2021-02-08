package com.luosico.controller;

import com.luosico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/2/8
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户注册
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String registerUser(@RequestBody Map map) {
        if (map != null && map.size() == 4 && userService.addUser(map)) {
            return "ok";
        } else {
            return "fail";
        }
    }
}
