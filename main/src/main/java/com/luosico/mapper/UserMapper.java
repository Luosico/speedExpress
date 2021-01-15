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
    public User findUserByUsername(@Param("username") String username);
}
