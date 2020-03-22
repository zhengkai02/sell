package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.resp.CarSpecResp;
import com.iwhalecloud.retail.offer.entity.CarSpec;

@Mapper
public interface CarSpecMapper extends BaseMapper<CarSpec> {

    List<CarSpecResp> selectListByCarBrandId(@Param("carBrandId") String carBrandId);
}
