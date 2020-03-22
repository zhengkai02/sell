package com.iwhalecloud.retail.offer.manager;

import java.util.Date;
import java.util.List;


import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandAddReq;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandModReq;
import com.iwhalecloud.retail.offer.dto.req.TblGoodsBrandQryReq;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandAddResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp;
import com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandTenantResp;
import com.iwhalecloud.retail.offer.entity.TblGoodsBrand;
import com.iwhalecloud.retail.offer.mapper.TblGoodsBrandMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandManager
 * @Author wangzhongbao
 * @Date 2019/5/13 10:07
 **/
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TblGoodsBrandManager {


    @Autowired
    private TblGoodsBrandMapper tblGoodsBrandMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    public TblGoodsBrandAddResp addGoodsBrand(TblGoodsBrandAddReq req) throws BaseException {
        log.info("TblGoodsBrandManager addGoodsBrand start, req = [{}]", req);
        String bandId = UidGeneator.getUIDStr();
        Date now = DBDateUtil.getCurrentDBDateTime();
        TblGoodsBrand tblGoodsBrand = new TblGoodsBrand();
        BeanUtils.copyProperties(req, tblGoodsBrand);
        tblGoodsBrand.setBrandId(bandId);
        tblGoodsBrand.setCreateTime(now);
        tblGoodsBrand.setStateDate(now);
        tblGoodsBrand.setState(CommonStateDef.ACTIVE);
        tblGoodsBrand.setCreateBy(getUserId());
        tblGoodsBrand.setModifyBy(getUserId());
        tblGoodsBrand.setModifyTime(now);

        if (req.getTmpUserDTO() != null) {
            tblGoodsBrand.setCreateBy(req.getTmpUserDTO().getUserId().toString());
        }
        tblGoodsBrandMapper.insert(tblGoodsBrand);
        TblGoodsBrandAddResp resp = new TblGoodsBrandAddResp();
        BeanUtils.copyProperties(tblGoodsBrand, resp);
        if (resp.getBrandImg() != null && !resp.getBrandImg().equals("")) {
            String brandImg = tblGoodsBrand.getBrandImg();
            if (StringUtils.isNotEmpty(brandImg)) {
                String substring = brandImg.substring(brandImg.indexOf('/') + 1);
                String token = FastdfsTokenUtil.getToken(substring);

                if (StringUtils.isNotEmpty(token)) {
                    resp.setBrandUrl(showUrl + brandImg + "?" + token);
                }
                else {
                    resp.setBrandUrl(showUrl + brandImg);
                }
            }
        }
        log.info("TblGoodsBrandManager addGoodsBrand end");
        return resp;
    }

    public Page<TblGoodsBrandQryResp> qryGoodsBrandList(TblGoodsBrandQryReq req) throws BaseException {
        log.info("TblGoodsBrandManager qryGoodsBrandList start, req = [{}]", req);
        if (null == req || null == req.getPageNo() || null == req.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }

        Page<TblGoodsBrandQryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<TblGoodsBrandQryResp> evaluationRespList = tblGoodsBrandMapper.qryGoodsBrandList(page, req);

        // 处理img路径
        formatBrandImg(evaluationRespList.getRecords());

        log.info("TblGoodsBrandManager qryGoodsBrandList end");
        return evaluationRespList;

    }

    /**
     * 查询租户下的品牌
     * @return
     */
    public List<TblGoodsBrandTenantResp> qryGoodsBrandByTenantId() throws BaseException {
        log.info("TblGoodsBrandManager qryGoodsBrandByTenantId ");
        List<TblGoodsBrandTenantResp> respList = tblGoodsBrandMapper.qryGoodsBrandByTenantId();
        log.info("TblGoodsBrandManager qryGoodsBrandByTenantId end");
        return  respList;
    }

    /**
     * @Author wangzhongbao
     * @Description 商品品牌路径处理
     * @Date 16:55 2019/5/15
     * @Param [evaluationRespList]
     * @return void
     **/
    private void formatBrandImg(List<TblGoodsBrandQryResp> evaluationRespList) throws BaseException {
        if (CollectionUtils.isNotEmpty(evaluationRespList)) {
            for (TblGoodsBrandQryResp tblGoodsBrandQryResp : evaluationRespList) {

                String brandImg = tblGoodsBrandQryResp.getBrandImg();
                if (StringUtils.isNotEmpty(brandImg)) {
                    String substring = brandImg.substring(brandImg.indexOf('/') + 1);
                    String token = FastdfsTokenUtil.getToken(substring);

                    if (StringUtils.isNotEmpty(token)) {
                        tblGoodsBrandQryResp.setBrandUrl(showUrl + brandImg + "?" + token);
                    }
                    else {
                        tblGoodsBrandQryResp.setBrandUrl(showUrl + brandImg);
                    }
                }
            }
        }
    }

    public int updateGoodsBrand(String brandId, TblGoodsBrandModReq req) throws BaseException {
        log.info("TblGoodsBrandManager updateGoodsBrand start, req = [{}]", req);
        TblGoodsBrand tblGoodsBrand = tblGoodsBrandMapper.selectById(brandId);
        if (null == tblGoodsBrand) {
            throw new BaseException(OfferBaseMessageCodeEnum.TBL_GOODS_BRAND_NOT_EXIST);
        }
        tblGoodsBrand.setBrandName(req.getBrandName());
        tblGoodsBrand.setBrandImg(req.getBrandImg());
        tblGoodsBrand.setBrandIntro(req.getBrandIntro());
        tblGoodsBrand.setIsHot(req.getIsHot());
        tblGoodsBrand.setStoreId(req.getStoreId());
        if (req.getTmpUserDTO() != null) {
            tblGoodsBrand.setModifyBy(req.getTmpUserDTO().getUserId().toString());
        }
        tblGoodsBrand.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        tblGoodsBrandMapper.updateById(tblGoodsBrand);
        log.info("TblGoodsBrandManager updateGoodsBrand end");
        return 1;
    }

    public int deleteGoodsBrand(List<String> brandIds) throws BaseException {
        checkGoodsBrandState(brandIds);
        return tblGoodsBrandMapper.deleteGoodsBrand(brandIds, getUserId());
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    /**
     * @Author wangzhongbao
     * @Description 校验批量删除的brandIds是否都为正常状态
     * @Date 14:45 2019/5/15
     * @Param [brandIds]
     * @return void
     **/
    private void checkGoodsBrandState(List<String> brandIds) throws BaseException {
        int rightNum = tblGoodsBrandMapper.goodsBrandStateNum(brandIds);
        if (brandIds.size() != rightNum) {
            throw new BaseException(OfferBaseMessageCodeEnum.TBL_GOODS_BRANDS_NOT_ALL_RIGHT);
        }
    }

    public List<TblGoodsBrandQryResp> qryAllGoodsBrandList() throws BaseException {
        List<TblGoodsBrandQryResp> tblGoodsBrandQryRespList = tblGoodsBrandMapper.qryAllGoodsBrandList();
        // img路径处理
        formatBrandImg(tblGoodsBrandQryRespList);
        return tblGoodsBrandQryRespList;
    }

    /**
     * @Author wangzhongbao
     * @Description 查询管理目录下的品牌,若无则返回全部商品品牌
     * @Date 17:00 2019/5/15
     * @Param [catgId]
     * @return java.util.List<com.iwhalecloud.retail.offer.dto.resp.TblGoodsBrandQryResp>
     **/
    public List<TblGoodsBrandQryResp> qryGoodsBrandInCatg(String catgId) throws BaseException {
        log.info("TblGoodsBrandManager qryGoodsBrandInCatg start, req = [{}]", catgId);

        List<TblGoodsBrandQryResp> tblGoodsBrandQryRespList = tblGoodsBrandMapper.qryGoodsBrandInCatg(catgId);

        if (CollectionUtils.isNotEmpty(tblGoodsBrandQryRespList)) {
            formatBrandImg(tblGoodsBrandQryRespList);
            return tblGoodsBrandQryRespList;
        }
        else {
            return qryAllGoodsBrandList();
        }
    }
}
