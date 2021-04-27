package com.luosico.mapper;


import com.luosico.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */

@Mapper
public interface UserMapper {
    /**
     * 通过账号查找用户
     *
     * @param username 账号
     * @return 用户信息
     */
    User findUserByUsername(@Param("username") String username);

    /**
     * 通过手机号码查找用户
     *
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    User findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 通过 username查找 userId
     * @param username 用户名
     * @return
     */
    String selectUserIdByUsername(@Param("username") String username);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return
     */
    int addUser(@Param("user") User user);

    /**
     * 查找用户名是否存在
     *
     * @param username 用户名
     * @return 存在：1 ; 不存在：0
     */
    int selectProperty(@Param("name") String username, @Param("val") String val);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(@Param("user") User user);

    /**
     * 查询手机号码
     * @param userId
     * @return
     */
    String selectPhoneNumber(@Param("userId") Integer userId);

    /**
     * 更新用户信息
     *
     * @param username    当前用户名
     * @param newUsername 新的用户名
     * @param name        姓名
     * @return 执行结果
     */
    int updateUserName(@Param("username") String username, @Param("newUsername") String newUsername, @Param("name") String name);

    /**
     * 更新手机号码
     * @param username
     * @param phoneNumber
     * @return
     */
    int updatePhoneNumber(@Param("username") String username, @Param("phoneNumber") String phoneNumber);
}
