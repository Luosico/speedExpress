package com.luosico.service;

import com.luosico.config.OrderStatus;
import com.luosico.domain.Region;
import com.luosico.user.UserUtil;
import com.luosico.util.RedisUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    UserUtil userUtil;

    @DubboReference
    RedisUtils redisUtil;

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
     * 查询所有区域
     *
     * @return
     */
    public List<Region> getAllRegion() {
        return userUtil.selectAllRegion();
    }

    /**
     * 增加区域
     *
     * @param regionName 区域名字
     */
    public boolean addRegion(String regionName) {
        if (!isEmpty(regionName)) {
            return userUtil.addRegion(regionName) == 1;
        }
        return false;
    }

    /**
     * 若全为 null或 "" 返回true
     *
     * @param strs String数组
     */
    public boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || "".equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析数组形式的订单状态为列表形式
     *
     * @param list
     */
    public List<OrderStatus> parseOrderStatus(List<String> list) {
        List<OrderStatus> orderStatuses = null;
        if (list != null && list.size() > 0) {
            orderStatuses = new ArrayList<>();
            for (String orderStatus : list) {
                orderStatuses.add(OrderStatus.valueOf(orderStatus));
            }
        }
        return orderStatuses;
    }
}
