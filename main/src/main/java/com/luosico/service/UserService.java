package com.luosico.service;

import com.luosico.domain.Address;
import com.luosico.domain.User;
import com.luosico.user.UserUtil;
import com.luosico.util.RedisUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 所有用户相关的服务
 * USER、COURIER、ADMIN
 * <p>
 * 操作mysql 以 select 开头
 * 操作redis、cookie 以 get 开头
 *
 * @Author: luo kai fa
 * @Date: 2021/1/14
 */

@Service
public class UserService {

    @Autowired
    SmsService smsService;

    @Autowired
    UtilService utilService;

    @DubboReference
    RedisUtils redisUtil;

    @DubboReference
    UserUtil userUtil;

    /**
     * 根据帐号查找用户
     *
     * @param username 帐号
     */
    public User findUserByUsername(String username) {
        return userUtil.selectUserByUsername(username);
    }

    /**
     * 根据手机号码查找用户
     *
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    public User findUserByPhoneNumber(String phoneNumber) {
        return userUtil.selectUserByPhoneNumber(phoneNumber);
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
            return 1 == userUtil.addUser(user);
        }
        return false;
    }

    /**
     * 查找是否唯一
     */
    public boolean isExit(String name, String val) {
        return userUtil.selectProperty(name, val) == 1;
    }

    /**
     * 更改密码
     *
     * @param phoneNumber 手机号码
     * @param smsCode     短信验证码
     * @param password    密码
     * @return
     */
    public String changePassword(String phoneNumber, String smsCode, String password) {
        String message = "";
        if (!utilService.isEmpty(phoneNumber, smsCode, password)) {
            //验证码正确
            if (validateSmsCode(phoneNumber, smsCode)) {
                User user = new User(password, phoneNumber);
                if (updateUser(user)) {
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
     * 更改密码
     *
     * @param username 用户名
     * @param password 新的密码
     * @return 执行结果
     */
    public boolean updatePassword(String username, String password) {
        if (!utilService.isEmpty(username, password)) {
            User user = new User();
            user.setUsername(username);
            user.setEncodePassword(password);

            return updateUser(user);
        }
        return false;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        if (user != null) {
            return userUtil.updateUser(user) == 1;
        }
        return false;
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
     * 判断 uid cookie 是否正确
     *
     * @return
     */
    public boolean UidIsCorrect(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //存在 cookie: uid
                if ("uid".equals(cookie.getName())) {
                    String cookieUid = cookie.getValue();
                    //redis中是否存在
                    return redisUtil.hasKey(cookieUid);
                }
            }
        }
        return false;
    }

    /**
     * 获取 userId
     *
     * @param cookies
     * @return userId
     */
    public String getUserIdByCookie(Cookie[] cookies) {
        String username = getUsernameByCookie(cookies);
        return selectUserIdByUsername(username);
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
    public String selectUserIdByUsername(String username) {
        return userUtil.selectUserIdByUsername(username);
    }

    /**
     * 在缓存中通过 username 查找 userId
     *
     * @param username 用户名
     * @return userId
     */
    public String getUserIdByUsername(String username) {
        return (String) redisUtil.hget(username, "uerId");
    }

    /**
     * 将 user信息存入 redis中
     *
     * @param uid      随机数字字母混合
     * @param username 用户名
     */
    public void setUserInfo(String uid, String username) {
        String userId = selectUserIdByUsername(username);
        User user  = selectUserByUsername(username);
        redisUtil.set(uid, username, 60 * 30);
        redisUtil.hset(username, "uid", uid, 60 * 30);
        redisUtil.hset(username, "userId", userId);
        redisUtil.hset(username, "phoneNumber", user.getPhoneNumber());
        redisUtil.hset(username, "name", user.getName());
    }

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    public boolean addAddress(Address address) {
        return userUtil.addAddress(address) == 1;
    }

    /**
     * 更新地址
     *
     * @param address
     * @return
     */
    public boolean updateAddress(Address address) {
        return userUtil.updateAddress(address) == 1;
    }

    /**
     * 查询用户所有地址
     *
     * @param userId
     * @return
     */
    public List<Address> selectAddressesByUserId(Integer userId) {
        return userUtil.selectAddressesByUserId(userId);
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    public boolean deleteAddress(Integer addressId) {
        return userUtil.deleteAddress(addressId) == 1;
    }

    /**
     * 获取电话号码
     *
     * @param cookies
     * @return
     */
    public String getPhoneNumber(Cookie[] cookies) {
        String username = getUsernameByCookie(cookies);
        return getPhoneNumberByUsername(username);
    }

    /**
     * 通过 username 获取 phoneNumber
     *
     * @param username
     * @return
     */
    public String getPhoneNumberByUsername(String username) {
        return (String) redisUtil.hget(username, "phoneNumber");
    }

    /**
     * 从数据库中查找手机号码
     *
     * @param userId
     * @return
     */
    public String selectPhoneNumberByUserId(String userId) {
        return userUtil.selectPhoneNumber(Integer.valueOf(userId));
    }

    /**
     * 根据 username 查找 手机号码
     *
     * @param username
     * @return
     */
    public String selectPhoneNumberByUsername(String username) {
        String userId = selectUserIdByUsername(username);
        return selectPhoneNumberByUserId(userId);
    }

    /**
     * 发送短信验证码
     *
     * @param cookies
     */
    public void sendSmsCode(Cookie[] cookies) {
        String phoneNumber = getPhoneNumber(cookies);
        smsService.sendSmsCode(phoneNumber);
    }

    /**
     * 发送短信验证码
     *
     * @param phoneNumber 手机号码
     */
    public void sendSmsCode(String phoneNumber) {
        smsService.sendSmsCode(phoneNumber);
    }

    /**
     * 更新用户信息
     *
     * @param username    当前用户名
     * @param newUsername 新的用户名
     * @param name        姓名
     * @return 执行结果
     */
    public boolean updateUserName(String username, String newUsername, String name) {
        if (userUtil.updateUserName(username, newUsername, name) == 1) {
            String uid = getUidByUsername(username);
            String userId = selectUserIdByUsername(username);
            String phoneNumber = getPhoneNumberByUsername(username);
            //更新Redis信息
            redisUtil.set(uid, newUsername, 60 * 30);
            redisUtil.del(username);
            redisUtil.hset(newUsername, "uid", uid, 60 * 30);
            redisUtil.hset(newUsername, "userId", userId);
            redisUtil.hset(newUsername, "phoneNumber", phoneNumber);
            redisUtil.hset(newUsername, "name", name);

            return true;
        }
        return false;
    }

    /**
     * 验证短信验证码
     *
     * @param phoneNumber 手机号码
     * @return 验证结果
     */
    public boolean validateSmsCode(String phoneNumber, String smsCode) {
        if (!utilService.isEmpty(phoneNumber, smsCode)) {
            Object temp = redisUtil.get(phoneNumber);
            if (temp != null && ((String) temp).equals(smsCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateSmsCode(Cookie[] cookies, String smsCode) {
        String phoneNumber = getPhoneNumber(cookies);
        return validateSmsCode(phoneNumber, smsCode);
    }

    /**
     * 更新手机号码
     *
     * @param username
     * @param phoneNumber
     * @return
     */
    public boolean updatePhoneNumber(String username, String phoneNumber) {
        if (userUtil.updatePhoneNumber(username, phoneNumber) == 1) {
            redisUtil.hset(username, "phoneNumber", phoneNumber);
            redisUtil.expire(username, 60 * 30);
            return true;
        }
        return false;
    }

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @return
     */
    public User selectUserByUsername(String username) {
        return userUtil.selectUserByUsername(username);
    }

    /**
     * 查找用户姓名
     *
     * @param username
     * @return
     */
    public String getNameByUsername(String username) {
        return (String) redisUtil.hget(username, "name");
    }

}
