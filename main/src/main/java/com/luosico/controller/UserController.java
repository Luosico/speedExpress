package com.luosico.controller;

import com.luosico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户名是否存在
     * @return true 存在；false 不存在
     */
    @GetMapping("/isExit")
    @ResponseBody
    public String usernameIsExit(@RequestParam("name")String name, @RequestParam("val") String val){
        if(userService.isExit(name,val)){
            return "true";
        }
        return "false";
    }


    /**
     * 更改密码
     */
    @ResponseBody
    @PutMapping("/changePassword")
    public String changePassword(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("smsCode") String smsCode,@RequestParam("password") String password){

        return null;
    }
}
