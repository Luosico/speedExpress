package com.luosico.mapper;

import com.luosico.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */

@Mapper
public interface UserMapper {
    public User findUserByUsername(@Param("username") String username);

    public User findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
