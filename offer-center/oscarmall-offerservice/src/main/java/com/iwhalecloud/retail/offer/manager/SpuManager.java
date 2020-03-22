package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.consts.SpuStateDef;
import com.iwhalecloud.retail.offer.dto.req.AddSpuReq;
import com.iwhalecloud.retail.offer.dto.req.DeleteSpuReq;
import com.iwhalecloud.retail.offer.dto.req.PageSpuReq;
import com.iwhalecloud.retail.offer.dto.req.UpdateSpuReq;
import com.iwhalecloud.retail.offer.dto.resp.PageSpuResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.Spu;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.SpuGoodsMapper;
import com.iwhalecloud.retail.offer.mapper.SpuMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * SPU
 * 
 * @author fanxiaofei
 * @date 2019/05/22
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SpuManager {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuGoodsMapper spuGoodsMapper;

    @Autowired
    private AttrMapper attrMapper;

    /**
     * 新增
     * 
     * @param req AddSpuReq
     */
    public void addSpu(AddSpuReq req) throws BaseException {
        log.info("SpuManager addSpu start, AddSpuReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getSpuName(), OfferBaseMessageCodeEnum.SPU_NAME_IS_NULL);

        Spu spu = new Spu();
        Date now = DBDateUtil.getCurrentDBDateTime();
        spu.setStateDate(now);
        spu.setCreateTime(now);
        spu.setSpuName(req.getSpuName());
        spu.setState(SpuStateDef.STATE_A);
        spu.setComments(req.getComments());
        spu.setSpuId(UidGeneator.getUIDStr());
        spu.setCreateBy(req.getCreateBy());
        spu.setModifyBy(req.getCreateBy());
        spu.setModifyTime(now);
        spuMapper.insert(spu);

        log.info("SpuManager addSpu end");
    }

    /**
     * 分页
     * 
     * @param req AddSpuReq
     * @return Page<PageSpuResp>
     */
    public Page<PageSpuResp> pageSpu(PageSpuReq req) throws BaseException {
        log.info("SpuManager pageSpu start, PageSpuReq = [{}]", req);

        Page<PageSpuResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<PageSpuResp> result = spuMapper.pageSpu(page, req);
        List<PageSpuResp> pageSpuRespList = result.getRecords();
        if (CollectionUtils.isEmpty(pageSpuRespList)) {
            log.info("SpuManager pageSpu end");
            return result;
        }
        // 规格属性列表名称
        for (PageSpuResp pageSpuResp : pageSpuRespList) {
            String skuAttrIds = pageSpuResp.getSkuAttrIds();
            if (StringUtils.isNotEmpty(skuAttrIds)) {
                String[] skuAttrIdList = skuAttrIds.split(",");
                StringBuilder skuAttrIdName = new StringBuilder();
                for (String skuAttrId : skuAttrIdList) {
                    Attr attr = attrMapper.selectById(skuAttrId);
                    AssertUtil.isNotNull(attr, OfferBaseMessageCodeEnum.ATTR_ID_IS_NOT_EXIST);
                    skuAttrIdName.append(attr.getAttrName());
                    skuAttrIdName.append(",");
                }
                skuAttrIdName = new StringBuilder(skuAttrIdName.substring(0, skuAttrIdName.length() - 1));
                pageSpuResp.setSkuAttrIdName(skuAttrIdName.toString());
            }
        }

        log.info("SpuManager pageSpu end");
        return result;
    }

    /**
     * 修改
     * 
     * @param req UpdateSpuReq
     */
    public void update(UpdateSpuReq req) throws BaseException {
        log.info("SpuManager update start, UpdateSpuReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getSpuId(), OfferBaseMessageCodeEnum.SPU_ID_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        Spu spu = spuMapper.selectById(req.getSpuId());
        AssertUtil.isNotNull(spu, OfferBaseMessageCodeEnum.SPU_ID_IS_ERROR);

        Date now = DBDateUtil.getCurrentDBDateTime();
        spu.setModifyTime(now);
        spu.setModifyBy(req.getModifyBy());
        spu.setSpuName(req.getSpuName());
        spu.setComments(req.getComments());
        spuMapper.updateById(spu);

        log.info("SpuManager update end");
    }

    /**
     * 删除
     * 
     * @param req DeleteSpuReq
     */
    public void delete(DeleteSpuReq req) throws BaseException {
        log.info("SpuManager delete start, DeleteSpuReq = [{}]", req);

        String spuId = req.getSpuId();
        AssertUtil.isNotNull(spuId, OfferBaseMessageCodeEnum.SPU_ID_IS_NULL);
        AssertUtil.isNotNull(req.getModifyBy(), OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        String goodsId = spuGoodsMapper.querySpuGoodsGoodsIdBySpuId(spuId);
        if (StringUtils.isNotEmpty(goodsId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.SPU_GOODS_IS_NOT_NULL);
        }

        Spu spu = spuMapper.selectById(spuId);
        Date now = DBDateUtil.getCurrentDBDateTime();
        spu.setStateDate(now);
        spu.setState(SpuStateDef.STATE_X);
        spu.setModifyTime(now);
        spu.setModifyBy(req.getModifyBy());
        spuMapper.updateById(spu);

        log.info("SpuManager delete end");
    }

}
