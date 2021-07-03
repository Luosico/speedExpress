package com.luosico.handle;

import com.luosico.domain.JsonStructure;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/5/30
 */
@Component
public class OrderHandle {
    public JsonStructure<String> tryAcceptOrder(@RequestBody Map<String, Integer> map, HttpServletRequest request){
        return new JsonStructure<>("fail","请不要重复操作");
    }
}
