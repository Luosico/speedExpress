package com.luosico.service;


import com.luosico.domain.*;
import com.luosico.mapper.AddressMapper;
import com.luosico.mapper.UserMapper;
import com.luosico.user.UserUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */

@Component
@DubboService
public class UserService implements UserUtil {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AddressMapper addressMapper;

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.SelectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer selectUserIdByUsername(String username) {
        return userMapper.selectUserIdByUsername(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int selectProperty(String username, String val) {
        return userMapper.selectProperty(username, val);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int addRegion(String regionName) {
        return addressMapper.addRegion(regionName);
    }

    @Override
    public int updateRegion(Region region) {
        return addressMapper.updateRegion(region);
    }

    @Override
    public int deleteRegion(Integer regionId) {
        return addressMapper.deleteRegion(regionId);
    }

    @Override
    public List<Region> selectAllRegion() {
        return addressMapper.selectAllRegion();
    }

    @Override
    public int addAddress(Address address) {
        return addressMapper.addAddress(address);
    }

    @Override
    public int updateAddress(Address address) {
        return addressMapper.updateAddress(address);
    }

    @Override
    public List<Address> selectAddressesByUserId(Integer userId) {
        return addressMapper.selectAddressesByUserId(userId);
    }

    @Override
    public int deleteAddress(Integer addressId) {
        return addressMapper.deleteAddress(addressId);
    }

    @Override
    public String selectPhoneNumber(Integer userId) {
        return userMapper.selectPhoneNumber(userId);
    }

    @Override
    public int updateUserName(String username, String newUsername, String name) {
        return userMapper.updateUserName(username, newUsername, name);
    }

    @Override
    public int updatePhoneNumber(String username, String phoneNumber) {
        return userMapper.updatePhoneNumber(username, phoneNumber);
    }

    @Override
    public int addCourier(Courier courier) {
        if (userMapper.addCourier(courier) == 0) {
            return 0;
        } else {
            return courier.getCourierId();
        }
    }

    @Override
    public int updateAuthority(Integer userId) {
        String authority = "ROLE_COURIER";
        return userMapper.updateAuthority(userId, authority);
    }

    @Override
    public Integer selectCourierIdByUserId(Integer userId) {
        return userMapper.selectCourierIdByUserId(userId);
    }

    @Override
    public boolean createWallet(Integer courierId) {
        Integer balance = 0;
        return userMapper.createWallet(courierId, balance) == 1;
    }

    @Override
    public boolean updateWallet(Integer courierId, Integer amount) {
        //账户余额
        int balance = selectWalletBalance(courierId);
        //大于账户余额
        if (-amount > balance) {
            return false;
        }
        balance = balance + amount;
        return userMapper.updateWallet(courierId, balance) == 1;
    }

    @Override
    public Integer selectWalletBalance(Integer courierId) {
        return userMapper.selectWalletBalance(courierId);
    }

    @Override
    public Integer selectTotalBalance(Integer courierId) {
        return userMapper.selectCourierTotalBalance(courierId);
    }

    @Override
    public Integer selectTotalBalance() {
        return userMapper.selectTotalBalance();
    }

    @Override
    public boolean addBalanceRecord(BalanceRecord record) {
        return userMapper.addBalanceRecord(record) == 1;
    }

    @Override
    public List<BalanceRecord> selectBalanceRecord(Integer courierId) {
        return userMapper.selectBalanceRecord(courierId);
    }
}
