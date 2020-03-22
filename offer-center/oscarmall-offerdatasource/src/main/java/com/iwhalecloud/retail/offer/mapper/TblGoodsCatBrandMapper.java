package com.iwhalecloud.retail.offer.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.entity.TblGoodsCatBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/29 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0<br>
 */
@Mapper
public interface TblGoodsCatBrandMapper extends BaseMapper<TblGoodsCatBrand> {

    void deleteGoodsCatBrandByIds(@Param("relaIds") List<String> relaIds, @Param("modifyBy") String modifyBy);
}
