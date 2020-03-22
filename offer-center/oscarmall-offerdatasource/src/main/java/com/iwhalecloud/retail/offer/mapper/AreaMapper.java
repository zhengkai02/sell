package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.AreaDTO;
import com.iwhalecloud.retail.offer.entity.Area;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    List<AreaDTO> selectListByLevel(@Param("level") Long level);

    List<AreaDTO> selectListByParentCode(@Param("parentCode") Long parentCode);

}
