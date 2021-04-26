package com.luosico.user;


import com.luosico.domain.Address;
import com.luosico.domain.Region;
import com.luosico.domain.User;

import java.util.List;

/**
 * dubbo服务调用接口
 *
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
public interface UserUtil {

    /**
     * 通过账号查找用户
     *
     * @param username 账号
     * @return 用户信息
     */
    User selectUserByUsername(String username);

    /**
     * 通过手机号码查找用户
     *
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    User selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过 username查找 userId
     * @param username 用户名
     * @return
     */
    String selectUserIdByUsername(String username);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return
     */
    int addUser(User user);

    /**
     * 查找用户名是否存在
     *
     * @param username 用户名
     * @return 存在：1 ; 不存在：0
     */
    int selectProperty(String username, String val);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    int updateUser(User user);

    /**
     *  添加区域
     * @param regionName 区域名称
     * @return 执行结果影响行数
     */
    int addRegion(String regionName);

    /**
     * 查询所有区域
     * @return
     */
    List<Region> selectAllRegion();

    /**
     * 添加地址
     * @param address
     * @return
     */
    int addAddress(Address address);

    /**
     * 更新地址
     */
    int updateAddress(Address address);

    /**
     * 获取用户所有地址
     * @param userId
     * @return
     */
    List<Address> selectAddressesByUserId(Integer userId);

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    int deleteAddress(Integer addressId);

}
