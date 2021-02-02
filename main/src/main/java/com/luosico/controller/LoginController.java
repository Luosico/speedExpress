package com.luosico.controller;

import com.luosico.exception.RestDemoException;
import com.luosico.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/11
 */

@Controller
public class LoginController {

    @Autowired
    SmsService smsService;

    @GetMapping("/")
    public String toLogin(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    /**
     * 获取短信验证码
     * @param phoneNumber 手机号码
     */
    @GetMapping("/smsCode")
    @ResponseBody
    public void getSmsCode(@RequestParam String phoneNumber){
        smsService.sendSmsCode(phoneNumber);
    }



    @RequestMapping(value = "/tryLogin")
    @ResponseBody
    public String tryLogin(){
        return "Hello world";
    }


    @RequestMapping(value = "/rest", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String rest_get(@RequestParam(value = "name", defaultValue = "John") String name) {

        return null;
    }

    @RequestMapping(value = "/dfdf", method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String rest_post(@RequestBody User user){

        return null;
    }

    @RequestMapping(value = "/dsfsdfa")
    //ResponseEntity可以包含相应的元数据（如头部信息和状态码）以及要转换成资源表述的对象,不需要再加 @ResponseBody
    public ResponseEntity<?> rest_entity(User user, UriComponentsBuilder builder){
        //UriComponentsBuilder会预先配置已知的信息，如host、端口以及Servlet内容

        //抛出指定异常，并交给异常处理器处理
        if(user == null){
            String data = null;
            throw new RestDemoException(data);
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/sdfsdf")
    @ResponseBody
    public String  rest_entity_simple(User user){

        if(user == null){
            String data = null;
            throw new RestDemoException(data);
        }
        //保证了HTTP状态码市200，没必要在显式设置了

        return "";
    }
}
