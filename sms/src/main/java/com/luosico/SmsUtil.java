package com.luosico;


import com.luosico.sms.Sms;
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
    @Override
    public void sendSmsCode(String phoneNumber) {

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
}
