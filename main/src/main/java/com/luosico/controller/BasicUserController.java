package com.luosico.controller;

import com.luosico.domain.JsonStructure;
import com.luosico.service.UserService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户基础信息控制器
 *
 * @Author: luo kai fa
 * @Date: 2021/2/8
 */

@Controller
public class BasicUserController {

    @Autowired
    UserService userService;

    @Autowired
    UtilService utilService;

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

    /**
     * 用户名是否存在
     *
     * @return true 存在；false 不存在
     */
    @GetMapping("/isExit")
    @ResponseBody
    public JsonStructure isExit(@RequestParam("name") String name, @RequestParam("val") String val, HttpServletRequest request) {
        if (!utilService.isEmpty(name, val)) {
            if("username".equals(name)){
                String username = userService.getUsernameByCookie(request.getCookies());
                //当前用户名
                if (username.equals(val)) {
                    return new JsonStructure<>();
                }
            }
            if (!userService.isExit(name, val)) {
                return new JsonStructure<>();
            }
        }
        return new JsonStructure("fail", "");
    }


    /**
     * 更改密码
     */
    @ResponseBody
    @PostMapping(value = "/changePassword", consumes = "application/json")
    public String changePassword(@RequestBody Map<String, String> map) {
        return userService.changePassword(map.get("phoneNumber"), map.get("smsCode"), map.get("password"));
    }
}
