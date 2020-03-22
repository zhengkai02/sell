package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.resp.CarSeriesResp;
import com.iwhalecloud.retail.offer.entity.CarSeries;

@Mapper
public interface CarSeriesMapper extends BaseMapper<CarSeries> {

    List<CarSeriesResp> selectListByCarBrandIdAndCarSepcId(@Param("carBrandId") String carBrandId,
                                               @Param("carSpecId") String carSpecId);
}
