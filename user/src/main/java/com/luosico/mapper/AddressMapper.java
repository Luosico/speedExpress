package com.luosico.mapper;

import com.luosico.domain.Address;
import com.luosico.domain.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
@Mapper
public interface AddressMapper {

    /**
     *  添加区域
     * @param regionName 区域名称
     * @return 执行结果影响行数
     */
    int addRegion(@Param("regionName") String regionName);

    /**
     * 更新区域
     *
     * @param region
     * @return
     */
    int updateRegion(Region region);

    /**
     * 删除区域
     *
     * @param regionId
     * @return
     */
    int deleteRegion(Integer regionId);

    /**
     * 查询所有区域
     * @return
     */
    List<Region> selectAllRegion();

    /**
     * 添加地址
     * @param address
     * @return
     */
    int addAddress(Address address);

    /**
     * 更新地址
     */
    int updateAddress(@Param("address") Address address);

    /**
     * 获取用户所有地址
     * @param userId
     * @return
     */
    List<Address> selectAddressesByUserId(@Param("userId") Integer userId);

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    int deleteAddress(@Param("addressId") Integer addressId);
}
