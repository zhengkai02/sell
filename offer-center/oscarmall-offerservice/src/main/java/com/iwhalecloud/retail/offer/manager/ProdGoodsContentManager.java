package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsQueryContentReq;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.AddGoodsContentReq;
import com.iwhalecloud.retail.offer.dto.resp.AddGoodsContentResp;
import com.iwhalecloud.retail.offer.entity.GoodsContentType;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.ProdGoodsContent;
import com.iwhalecloud.retail.offer.mapper.GoodsContentTypeMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsContentMapper;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProdGoodsContentManager {

    @Autowired
    private ProdGoodsMapper prodGoodsMapper;

    @Autowired
    private GoodsContentTypeMapper goodsContentTypeMapper;

    @Autowired
    private ProdGoodsContentMapper prodGoodsContentMapper;

    @Autowired
    private CommonManager commonManager;

    @Autowired
    private OfferManager offerManager;

    public AddGoodsContentResp addGoodsContent(AddGoodsContentReq request) throws BaseException {
        log.info("ProdGoodsContentManager addGoodsContent start, AddGoodsContentReq = [{}]", request);

        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getGoodsId(), OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        AssertUtil.isNotEmpty(request.getContentId(), OfferBaseMessageCodeEnum.CONTENT_ID_IS_NULL);
        AssertUtil.isNotNull(request.getGoodsContentType(), OfferBaseMessageCodeEnum.GOODS_CONTENT_TYPE_IS_NULL);

        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(request.getGoodsId(), null);
        AssertUtil.isNotNull(prodGoods, OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnNameDef.GOODS_CONTENT_TYPE, request.getGoodsContentType());
        queryWrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        GoodsContentType goodsContentType = goodsContentTypeMapper.selectOne(queryWrapper);
        AssertUtil.isNotNull(goodsContentType, OfferBaseMessageCodeEnum.GOODS_CONTENT_TYPE_NOT_EXIST);

        ProdGoodsQueryContentReq req = new ProdGoodsQueryContentReq();
        req.setGoodsId(request.getGoodsId());
        req.setGoodsContentType(request.getGoodsContentType());
        req.setDeviceType(request.getDeviceType());
        List<ProdGoodsContent> prodGoodsContents = prodGoodsContentMapper.selectGoodsContentByGoodsId(req);
        if (CollectionUtils.isNotEmpty(prodGoodsContents)) {
            prodGoodsContents.forEach(prodGoodsContent -> {
                prodGoodsContent.setStateDate(DBDateUtil.getCurrentDBDateTime());
                prodGoodsContent.setState(CommonStateDef.INACTIVE);
                prodGoodsContent.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                prodGoodsContent.setModifyBy(request.getUserId());
                prodGoodsContentMapper.updateById(prodGoodsContent);
            });
        }

        ProdGoodsContent prodGoodsContent = new ProdGoodsContent();
        prodGoodsContent.setGoodsContentId(UidGeneator.getUIDStr());
        prodGoodsContent.setGoodsId(request.getGoodsId());
        prodGoodsContent.setContentId(request.getContentId());
        prodGoodsContent.setGoodsContentType(request.getGoodsContentType());
        prodGoodsContent.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsContent.setState(CommonStateDef.ACTIVE);
        prodGoodsContent.setStateDate(DBDateUtil.getCurrentDBDateTime());
        prodGoodsContent.setCreateBy(request.getUserId());
        prodGoodsContent.setModifyBy(request.getUserId());
        prodGoodsContent.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsContent.setDeviceType(request.getDeviceType());

        prodGoodsContentMapper.insert(prodGoodsContent);

        // 清空缓存
        commonManager.cacheClear(request.getGoodsId());

        AddGoodsContentResp response = new AddGoodsContentResp();
        response.setGoodsContentId(prodGoodsContent.getGoodsContentId());

        log.info("ProdGoodsContentManager addGoodsContent end");
        return response;
    }


    public void delGoodsContent(String goodsContentId) throws BaseException {
        log.info("ProdGoodsContentManager delGoodsContent start, goodsContentId = [{}]", goodsContentId);

        AssertUtil.isNotEmpty(goodsContentId, OfferBaseMessageCodeEnum.PARAM_ERROR);
        ProdGoodsContent prodGoodsContent = prodGoodsContentMapper.selectGoodsContentById(goodsContentId);
        AssertUtil.isNotNull(prodGoodsContent, OfferBaseMessageCodeEnum.GOODS_CONTENT_NOT_EXIST);

        String modifyBy = getUserId();

        prodGoodsContent.setState(CommonStateDef.INACTIVE);
        prodGoodsContent.setStateDate(DBDateUtil.getCurrentDBDateTime());
        prodGoodsContent.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        prodGoodsContent.setModifyBy(modifyBy);
        prodGoodsContentMapper.updateById(prodGoodsContent);

        // 清空缓存
        commonManager.cacheClear(prodGoodsContent.getGoodsId());

        log.info("ProdGoodsContentManager delGoodsContent end");
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    public ArrayList<String> getContentIds(ProdGoodsQueryContentReq req) throws BaseException {
        log.info("ProdGoodsContentManager getContentIds start, req = [{}]", req);
        List<String> contentIds = offerManager.qryGoodsContentList(req);
        ArrayList<String> resp = new ArrayList<>();
        resp.addAll(contentIds);
        log.info("ProdGoodsContentManager getContentIds start, resp = [{}]", resp);
        return resp;
    }

}
