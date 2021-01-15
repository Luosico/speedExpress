package com.luosico.service;

import com.luosico.domain.User;
import com.luosico.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/14
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findUserByUsername(String username){
        User user = userMapper.findUserByUsername(username);
        return user;
    }
}
