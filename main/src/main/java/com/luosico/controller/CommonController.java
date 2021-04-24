package com.luosico.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luosico.domain.Region;
import com.luosico.service.RequestService;
import com.luosico.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/17
 * <p>
 * 通用控制器
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    RequestService requestService;

    @Autowired
    UtilService utilService;

    /**
     * 获取当前请求的 username
     *
     * @param request
     * @return username
     */
    @GetMapping("username")
    public String getUsername(HttpServletRequest request) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        String username = requestService.getUsernameByCookie(cookies);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    /**
     * 获取支持的所有区域
     */
    @GetMapping("regions")
    public Map<String, List<Region>> getAllRegions() {
        List<Region> regions = utilService.getAllRegion();
        HashMap<String, List<Region>> map = new HashMap<>();
        map.put("regions", regions);
        return map;
    }

    /**
     * 增加区域
     *
     * @param regionName 区域名字
     */
    @PostMapping("region")
    public String addRegion(@RequestBody String regionName) {
        if (utilService.addRegion(regionName)) {
            return "ok";
        }
        return "false";
    }

}
