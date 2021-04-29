package com.luosico.mapper;

import com.luosico.domain.Express;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@Mapper
public interface ExpressMapper {
        /**
         * 插入快递信息
         * @param express
         * @return
         */
        int addExpress(Express express);

        Express selectExpress(Integer expressId);
}
