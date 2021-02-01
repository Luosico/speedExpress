package com.luosico;


import com.luosico.sms.Sms;
import com.luosico.util.RedisUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * 短信工具类
 *
 * @Author: luo kai fa
 * @Date: 2021/1/19
 */

@Component
@DubboService
public class SmsUtil implements Sms {

    @DubboReference
    RedisUtils redisUtil;

    @Override
    public void sendSmsCode(String phoneNumber) {
        sendSmsCode(phoneNumber,6);
    }

    @Override
    public void sendSmsCode(String phoneNumber, int length) {
        String smsCode = getSmsCode(length);
        setExpireTime(phoneNumber,smsCode);
        System.out.println("手机号码: " + phoneNumber + " 收到的验证码为: " + smsCode);
    }

    @Override
    public void sendSmsMessage(String phoneNumber, String message) {

    }

    @Override
    public String getSmsCode(){
        return getSmsCode(6);
    }

    @Override
    public String getSmsCode(int length) {
        return createSmsCode(length);
    }

    /**
     * 生成随机数
     * @param length 随机数的位数
     * @return
     */

    private String createSmsCode(int length){
        StringBuilder smsCode = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int temp = (int) (Math.random()*10);
            smsCode.append(temp);
        }
        return smsCode.toString();
    }

    /**
     * 在Redis中存储键并设置过期时间
     * @param phoneNumber 手机号码
     * @param smsCode   验证码
     */
    private void setExpireTime(String phoneNumber, String smsCode){
        redisUtil.set(phoneNumber,smsCode);
        redisUtil.expire(phoneNumber,60);
    }
}
