package com.luosico.controller;

import com.luosico.domain.Courier;
import com.luosico.domain.JsonStructure;
import com.luosico.service.UserService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户功能控制器
 *
 * @Author: luo kai fa
 * @Date: 2021/3/26
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UtilService utilService;

    @GetMapping("main")
    public String toMain() {
        return "user/main";
    }

    @GetMapping("userInfo")
    public String toUserInfo() {
        return "user/userInfo";
    }

    @GetMapping("addOrder")
    public String toAddOrder() {
        return "user/addOrder";
    }

    @GetMapping("order")
    public String toOrder() {
        return "user/order";
    }

    @GetMapping("address")
    public String toAddress() {
        return "user/address";
    }

    @GetMapping("feedback")
    public String toFeedback() {
        return "user/feedback";
    }

    @GetMapping("becomeSender")
    public String toBecomeSender() {
        return "user/becomeSender";
    }

    /**
     * 成为快取员
     * @param map
     * @param request
     * @return
     */
    @PostMapping("courier")
    @ResponseBody
    public JsonStructure becomeCourier(@RequestBody Map<String,String> map, HttpServletRequest request){
        String identityId = map.get("identityId");
        String smsCode = map.get("smsCode");
        if (!utilService.isEmpty(identityId, smsCode)){
            if(userService.validateSmsCode(request.getCookies(), smsCode)){
                Integer userId = userService.getUserIdByCookie(request.getCookies());
                Courier courier = new Courier();
                courier.setUserId(userId);
                courier.setIdentityId(identityId);

                //成功成为快取员
                if(userService.becomeCourier(courier)){
                    return new JsonStructure();
                }else{
                    return new JsonStructure("fail","服务器出现异常");
                }
            }
            return new JsonStructure("fail","验证码错误");
        }else{
            return new JsonStructure("fail","内容不能为空");
        }
    }

}
