package com.iwhalecloud.retail.offer.manager;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwhalecloud.retail.offer.entity.GoodsContentType;
import com.iwhalecloud.retail.offer.mapper.GoodsContentTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GoodsContentTypeManager {

    @Autowired
    private GoodsContentTypeMapper goodsContentTypeMapper;

    public List<GoodsContentType> qryGoodsContentType() {
        log.info("GoodsContentTypeManager qryGoodsContentType start");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<GoodsContentType> goodsContentTypeList = goodsContentTypeMapper.selectList(queryWrapper);
        log.info("GoodsContentTypeManager qryGoodsContentType end");
        return goodsContentTypeList;
    }
}
