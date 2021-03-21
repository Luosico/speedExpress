package com.luosico.service;

import com.luosico.domain.User;
import com.luosico.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/14
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SmsService smsService;

    /**
     * 根据帐号查找用户
     *
     * @param username 帐号
     */
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        return user;
    }

    /**
     * 根据手机号码查找用户
     *
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    public User findUserByPhoneNumber(String phoneNumber) {
        return userMapper.findUserByPhoneNumber(phoneNumber);
    }

    /**
     * 添加用户
     *
     * @param map 用户信息
     * @return 成功返回true，失败返回false
     */
    public boolean addUser(Map map) {
        String smsCode = (String) map.get("smsCode");
        String phoneNumber = (String) map.get("phoneNumber");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        if (isEmpty(username) || isEmpty(password)) {
            return false;
        }
        if (smsService.isCorrect(phoneNumber, smsCode)) {
            String encodePassword = new BCryptPasswordEncoder().encode(password);
            User user = new User(username, encodePassword, phoneNumber);
            String authority = authoritiesToString((ArrayList<SimpleGrantedAuthority>) user.getAuthorities());
            int row = userMapper.addUser(user, authority);
            if (row == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找是否唯一
     */
    public boolean isExit(String name, String val) {
        return userMapper.selectProperty(name, val) == 1;
    }

    public String changePassword(String phoneNumber, String smsCode, String password) {
        String message = "";
        if (!isEmpty(phoneNumber, smsCode, password)) {
            //验证码正确
            if (smsService.isCorrect(phoneNumber, smsCode)) {
                User user = new User(password, phoneNumber);
                if(userMapper.updateUser(user)!=0){
                    message ="ok";
                }
            }else{
                message = "验证码或手机号码错误";
            }
        }else{
            message = "不能为空";
        }
        return message;
    }

    private boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || "".equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将权限集合转换成字符串
     *
     * @param list 权限集合
     */
    private String authoritiesToString(ArrayList<SimpleGrantedAuthority> list) {
        StringBuilder authority = new StringBuilder();
        for (SimpleGrantedAuthority temp : list) {
            authority.append(temp.getAuthority());
        }
        return authority.toString();
    }
}
