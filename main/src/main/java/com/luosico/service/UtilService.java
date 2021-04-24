package com.luosico.service;

import com.luosico.domain.Region;
import com.luosico.mapper.AddressMapper;
import com.luosico.util.RedisUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 工具服务类
 *
 * @Author: luo kai fa
 * @Date: 2021/4/24
 */

@Service
public class UtilService {

    @DubboReference
    RedisUtils redisUtil;

    @Autowired
    AddressMapper addressMapper;

    /**
     * 生成随机字母数字组合
     *
     * @param length 长度
     * @return 随机组合
     */
    public static String getCharAndNum(int length) {
        if (length < 8) {
            length = 8;
        }
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //字母
            if ("char".equals(charOrNum)) {
                //大小写
                int characterCase = random.nextInt(2) % 2 == 0 ? 65 : 97;
                result.append((char) (characterCase + random.nextInt(26)));
            } else {
                //数字
                result.append(random.nextInt(10));
            }
        }
        return result.toString();
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
     * 设置  uid string 类型
     * username hash 类型
     *
     * @param uid
     * @param username
     */
    public void setUidAndUsername(String uid, String username) {
        redisUtil.set(uid, username, 60 * 30);
        redisUtil.hset(username, "uid", uid, 60 * 30);
    }

    /**
     * 查询所有区域
     *
     * @return
     */
    public List<Region> getAllRegion() {
        return addressMapper.selectAllRegion();
    }

    /**
     * 增加区域
     *
     * @param regionName 区域名字
     */
    public boolean addRegion(String regionName) {
        if (!isEmpty(regionName)) {
            return addressMapper.addRegion(regionName) == 1;
        }
        return false;
    }

    /**
     * 若全为 null或 "" 返回true
     *
     * @param strs String数组
     */
    private boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || "".equals(str)) {
                return true;
            }
        }
        return false;
    }
}