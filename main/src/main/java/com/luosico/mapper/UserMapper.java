package com.luosico.mapper;

import com.luosico.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */

@Mapper
public interface UserMapper {
    /**
     * 通过账号查找用户
     * @param username 账号
     * @return 用户信息
     */
    public User findUserByUsername(@Param("username") String username);

    /**
     * 通过手机号码查找用户
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    public User findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 新增用户
     * @param user 用户信息
     * @return
     */
    public int addUser(@Param("user") User user, @Param("authorities") String authorities);
}
