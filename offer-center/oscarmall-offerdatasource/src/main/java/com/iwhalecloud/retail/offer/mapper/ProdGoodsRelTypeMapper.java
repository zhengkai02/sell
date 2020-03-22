package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.ProdGoodsRelTypeDTO;
import com.iwhalecloud.retail.offer.entity.ProdGoodsRelType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author fanxiaofei
 * @date 2019/02/26
 */
@Mapper
public interface ProdGoodsRelTypeMapper extends BaseMapper<ProdGoodsRelType> {


    /**
     * 查所有商品关系类型
     * @return List<ProdGoodsRelTypeDTO>
     */
    List<ProdGoodsRelTypeDTO> listProdGoodsRelType();

}
