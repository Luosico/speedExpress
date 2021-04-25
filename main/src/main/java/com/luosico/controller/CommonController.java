package com.luosico.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luosico.domain.Address;
import com.luosico.domain.JsonStructure;
import com.luosico.domain.Region;
import com.luosico.service.UserService;
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
    UserService userService;

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
        String username = userService.getUsernameByCookie(cookies);
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

    /**
     * 添加地址
     */
    @PostMapping("address")
    public JsonStructure addAddress(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Address address;
        if ((address = getAddress(map, request)) != null) {
            //添加地址成功
            if (userService.addAddress(address)) {
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 更新用户地址
     */
    @PutMapping("address")
    public JsonStructure updateAddress(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Address address;
        if ((address = getAddress(map, request)) != null) {
            //修改地址成功
            if (userService.updateAddress(address)) {
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 查询用户的所有地址
     *
     * @param request
     * @return
     */
    @GetMapping("address")
    public JsonStructure<List<Address>> selectAddressesByUserId(HttpServletRequest request) {
        String userId = userService.getUserIdByCookie(request.getCookies());
        List<Address> addressList = userService.selectAddressesByUserId(Integer.valueOf(userId));
        return new JsonStructure<List<Address>>("ok", addressList);
    }

    /**
     * 删除地址
     * @return
     */
    @DeleteMapping("address")
    public JsonStructure deleteAddress(@RequestBody Map<String, String> map){
        String addressId = map.get("addressId");
        if (!utilService.isEmpty(addressId)){
            if (userService.deleteAddress(Integer.valueOf(addressId))){
                return new JsonStructure();
            }
        }
        return new JsonStructure("fail", "");
    }

    /**
     * 获取 address信息
     * 存入 Address对象
     *
     * @return true 成功； false 失败或数据有错误
     */
    private Address getAddress(Map<String, String> map, HttpServletRequest request) {
        String username = userService.getUsernameByCookie(request.getCookies());
        String addressId = map.get("addressId");
        String regionId = map.get("regionId");
        String pickUpAddress = map.get("pickUpAddress");
        String receiveAddress = map.get("receiveAddress");
        if (!utilService.isEmpty(username, regionId, pickUpAddress, receiveAddress)) {
            String userId = userService.getUserIdByUsername(username);
            Address address = new Address(Integer.valueOf(userId), Integer.valueOf(regionId), pickUpAddress, receiveAddress);
            if (!utilService.isEmpty(addressId)) {
                address.setAddressId(Integer.valueOf(addressId));
            }
            return address;
        }
        return null;
    }

}
