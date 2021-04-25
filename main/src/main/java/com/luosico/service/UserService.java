package com.luosico.service;

import com.luosico.domain.Address;
import com.luosico.domain.User;
import com.luosico.mapper.AddressMapper;
import com.luosico.mapper.UserMapper;
import com.luosico.util.RedisUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 所有用户相关的服务
 * USER、COURIER、ADMIN
 *
 * @Author: luo kai fa
 * @Date: 2021/1/14
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    SmsService smsService;

    @Autowired
    UtilService utilService;

    @DubboReference
    RedisUtils redisUtil;

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
        if (utilService.isEmpty(username) || utilService.isEmpty(password)) {
            return false;
        }
        if (smsService.isCorrect(phoneNumber, smsCode)) {
            User user = new User(username, password, phoneNumber);
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
        if (!utilService.isEmpty(phoneNumber, smsCode, password)) {
            //验证码正确
            if (smsService.isCorrect(phoneNumber, smsCode)) {
                User user = new User(password, phoneNumber);
                if (userMapper.updateUser(user) != 0) {
                    message = "ok";
                }
            } else {
                message = "验证码或手机号码错误";
            }
        } else {
            message = "不能为空";
        }
        return message;
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

    /**
     * 登录成功，设置cookie, 并存入redis
     *
     * @param response
     * @param username
     */
    public void loginSuccessAddCookie(HttpServletResponse response, String username) {
        String uid = UtilService.getCharAndNum(8);
        Cookie cookie = new Cookie("uid", uid);
        //作用路径
        cookie.setPath("/");
        //过期时间 秒
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);

        //存入redis
        setUserInfo(uid, username);
    }

    /**
     * 根据 cookie 的 uid 获取当前请求的 username
     *
     * @param cookies
     * @return username
     */
    public String getUsernameByCookie(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("uid".equals(cookie.getName())) {
                    String uid = cookie.getValue();
                    return getUsernameByUid(uid);
                }
            }
        }
        return null;
    }

    /**
     * 获取 userId
     *
     * @param cookies
     * @return userId
     */
    public String getUserIdByCookie(Cookie[] cookies) {
        String username = getUsernameByCookie(cookies);
        return getUserIdByUsername(username);
    }

    /**
     * 通过 uid 获取 username
     *
     * @param uid
     * @return username
     */
    public String getUsernameByUid(String uid) {
        return (String) redisUtil.get(uid);
    }

    /**
     * 通过 username 获取 uid
     * 在 redis中
     *
     * @param username
     * @return
     */
    public String getUidByUsername(String username) {
        String userId = (String) redisUtil.hget(username, "userId");
        return userId;
    }

    /**
     * 通过 username 查找 userId
     *
     * @param username 用户名
     * @return userId
     */
    public String getUserIdByUsername(String username) {
        return userMapper.selectUserIdByUsername(username);
    }

    /**
     * 将 user信息存入 redis中
     *
     * @param uid      随机数字字母混合
     * @param username 用户名
     */
    public void setUserInfo(String uid, String username) {
        redisUtil.set(uid, username, 60 * 30);
        redisUtil.hset(username, "uid", uid, 60 * 30);
        redisUtil.hset(username, "userId", getUserIdByUsername(username));
    }

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    public boolean addAddress(Address address) {
        return addressMapper.addAddress(address) == 1;
    }

    /**
     * 更新地址
     *
     * @param address
     * @return
     */
    public boolean updateAddress(Address address) {
        return addressMapper.updateAddress(address) == 1;
    }

    /**
     * 查询用户所有地址
     *
     * @param userId
     * @return
     */
    public List<Address> selectAddressesByUserId(Integer userId) {
        return addressMapper.selectAddressesByUserId(userId);
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    public boolean deleteAddress(Integer addressId) {
        return addressMapper.deleteAddress(addressId) == 1;
    }
}
