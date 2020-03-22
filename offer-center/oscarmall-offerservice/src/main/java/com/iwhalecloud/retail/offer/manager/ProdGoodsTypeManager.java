package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.offer.entity.ProdGoodsType;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsTypeMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsTypeManager {
    
    @Autowired
    ProdGoodsTypeMapper prodGoodsTypeMapper;

    public List<ProdGoodsType> qryAllProdGoodsType() {
        log.info("ProdGoodsTypeManager qryAllProdGoodsType start");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<ProdGoodsType> resp = prodGoodsTypeMapper.selectList(queryWrapper);

        if (null == resp) {
            resp = new ArrayList<>();
        }
        log.info("ProdGoodsTypeManager qryAllProdGoodsType end");
        return resp;
    }

}
