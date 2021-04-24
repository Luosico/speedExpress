package com.luosico.mapper;

import com.luosico.domain.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/24
 */
@Mapper
public interface AddressMapper {
    int addRegion(@Param("regionName") String regionName);

    List<Region> selectAllRegion();
}
