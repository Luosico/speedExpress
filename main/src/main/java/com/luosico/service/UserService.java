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
            int row = userMapper.addUser(user,authority);
            if (row == 1){
                return true;
            }
        }
        return false;
    }

    public boolean addUses() {

        return true;
    }

    private boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 将权限集合转换成字符串
     * @param list 权限集合
     */
    private String authoritiesToString(ArrayList<SimpleGrantedAuthority> list){
        StringBuilder authority = new StringBuilder();
        for(SimpleGrantedAuthority temp : list){
            authority.append(temp.getAuthority());
        }
        return authority.toString();
    }
}
