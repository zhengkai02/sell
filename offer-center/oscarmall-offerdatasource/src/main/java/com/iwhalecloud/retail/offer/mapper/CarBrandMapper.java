package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.resp.CarBrandResp;
import com.iwhalecloud.retail.offer.entity.CarBrand;

@Mapper
public interface CarBrandMapper extends BaseMapper<CarBrand> {

    List<CarBrandResp> selectAllList();
}
