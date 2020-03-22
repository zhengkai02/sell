package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.entity.ProdGoodsPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @ClassName ProdGoodsPicMapper
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/21 15:55
 **/
@Mapper
public interface ProdGoodsPicMapper extends BaseMapper<ProdGoodsPic> {

    List<String> qryPicUrl(@Param("goodsId") String goodsId);
}
