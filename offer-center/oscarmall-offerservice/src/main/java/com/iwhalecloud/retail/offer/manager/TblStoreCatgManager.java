package com.iwhalecloud.retail.offer.manager;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.AddTblStoreCatgReq;
import com.iwhalecloud.retail.offer.dto.req.TblStoreCatgQryReq;
import com.iwhalecloud.retail.offer.dto.resp.AddTblStoreCatgResp;
import com.iwhalecloud.retail.offer.dto.resp.TblStoreCatgResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.entity.Store;
import com.iwhalecloud.retail.offer.entity.TblStoreCatg;
import com.iwhalecloud.retail.offer.mapper.StoreMapper;
import com.iwhalecloud.retail.offer.mapper.TblStoreCatgMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.0
 * @ClassName TblStoreCatgManager
 * @Author wangzhongbao
 * @Date 2019/4/28 15:34
 **/
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TblStoreCatgManager {

    @Autowired
    private TblStoreCatgMapper tblStoreCatgMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    public AddTblStoreCatgResp create(AddTblStoreCatgReq req) throws BaseException {
        log.info("TblStoreCatgManager create start, request = [{}]", req);

        AssertUtil.isNotNull(req.getName(), OfferBaseMessageCodeEnum.NAME_IS_NULL);

        if (checkStore(req.getStoreId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        }

        TblStoreCatg tblStoreCatg = new TblStoreCatg();

        BeanUtils.copyProperties(req, tblStoreCatg);
        String userId = getUserId();
        tblStoreCatg.setCatId(UidGeneator.getUID().toString());
        tblStoreCatg.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        tblStoreCatg.setCreateBy(userId);
        tblStoreCatg.setModifyBy(userId);
        tblStoreCatg.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        tblStoreCatg.setState(CommonStateDef.ACTIVE);

        tblStoreCatgMapper.insert(tblStoreCatg);

        AddTblStoreCatgResp resp = new AddTblStoreCatgResp();
        resp.setCatId(tblStoreCatg.getCatId());

        log.info("TblStoreCatgManager create end");
        return resp;
    }

    private String getUserId() throws BaseException {
        UserDTO userDTO = HttpSessionUtil.get();
        if (null == userDTO) {
            throw new BaseException(OfferBaseMessageCodeEnum.UPDATE_STAFF_ID_IS_NULL);
        }
        return userDTO.getUserId().toString();
    }

    public AddTblStoreCatgResp updateStoreCatg(AddTblStoreCatgReq req, String catId) throws BaseException {
        log.info("TblStoreCatgManager updateStoreCatg start, request = [{}]", req);

        AssertUtil.isNotNull(req.getName(), OfferBaseMessageCodeEnum.NAME_IS_NULL);
        AssertUtil.isNotNull(req.getStoreId(), OfferBaseMessageCodeEnum.STORE_ID_IS_NULL);

        if (checkStore(req.getStoreId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_IS_NOT_EXISTS);
        }

        TblStoreCatg tblStoreCatg = tblStoreCatgMapper.selectById(catId);
        if (null == tblStoreCatg) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_IS_NOT_EXISTS);
        }

        BeanUtils.copyProperties(req, tblStoreCatg);
        tblStoreCatg.setCatId(catId);
        tblStoreCatg.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        tblStoreCatg.setModifyBy(getUserId());

        tblStoreCatgMapper.updateById(tblStoreCatg);

        AddTblStoreCatgResp resp = new AddTblStoreCatgResp();
        resp.setCatId(tblStoreCatg.getCatId());

        log.info("TblStoreCatgManager updateStoreCatg end");
        return resp;
    }

    /**
     * @Author wangzhongbao
     * @Description 判断店铺是否存在
     * @Date 18:22 2019/4/29
     * @Param [storeId]
     * @return boolean
     **/
    private boolean checkStore(String storeId) {
        Store tblStore = storeMapper.selectById(storeId);
        return tblStore == null;
    }

    public AddTblStoreCatgResp delStoreCatg(String catId) throws BaseException {
        log.info("TblStoreCatgManager delStoreCatg start, catId = [{}]", catId);

        TblStoreCatg tblStoreCatg = tblStoreCatgMapper.selectById(catId);
        if (null == tblStoreCatg) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_IS_NOT_EXISTS);
        }

        // 校验是否含有子目录
        if (checkChildNode(catId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_HAS_CHILD_NODE);
        }

        // 校验是否有生效商品
        if (checkEffGoods(catId)) {
            throw new BaseException(OfferBaseMessageCodeEnum.STORE_CATG_HAS_EFF_GOODS);
        }


        tblStoreCatgMapper.deleteStoreCatgById(catId, getUserId());

        AddTblStoreCatgResp resp = new AddTblStoreCatgResp();
        resp.setCatId(catId);

        log.info("TblStoreCatgManager delStoreCatg end");
        return resp;
    }

    /**
     * @Author wangzhongbao
     * @Date 16:33 2019/4/29
     * @Param [catId]
     * @return boolean
     */
    private boolean checkEffGoods(String catId) {
        List<ProdGoods> prodGoodsList = tblStoreCatgMapper.qryEffectiveGoodsBycatId(catId);
        return CollectionUtils.isNotEmpty(prodGoodsList);
    }

    /**
     * @Author wangzhongbao
     * @Description 查询是否有子目录
     * @Date 16:33 2019/4/29
     * @Param [catId]
     * @return boolean
     **/
    private boolean checkChildNode(String catId) {
        List<TblStoreCatg> tblStoreCatgList = tblStoreCatgMapper.qryChildNode(catId);
        return CollectionUtils.isNotEmpty(tblStoreCatgList);
    }

    public List<TblStoreCatgResp> qryStoreCatg(TblStoreCatgQryReq req) throws BaseException {
        log.info("TblStoreCatgManager qryStoreCatg start, request = [{}]", req);

        List<TblStoreCatgResp> tblStoreCatgList = tblStoreCatgMapper.qryStoreCatgOfPage(req);

        List<TblStoreCatgResp> rootCatgList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tblStoreCatgList)) {
            for (TblStoreCatgResp tblStoreCatgResp : tblStoreCatgList) {
                tblStoreCatgResp.setRealLogo(tblStoreCatgResp.getLogo());
                getRealLogo(tblStoreCatgResp);

                if (StringUtils.isEmpty(tblStoreCatgResp.getParentId())) {
                    rootCatgList.add(tblStoreCatgResp);
                }
            }

            // 获取子节点
            appendGoodsCatChildren(rootCatgList, tblStoreCatgList);
        }

        log.info("TblStoreCatgManager qryStoreCatg end");
        return rootCatgList;
    }

    private void getRealLogo(TblStoreCatgResp tblStoreCatgResp) throws BaseException {
        if (StringUtils.isNotEmpty(tblStoreCatgResp.getLogo())) {
            String substring = tblStoreCatgResp.getLogo().substring(tblStoreCatgResp.getLogo().indexOf('/') + 1);
            String token = FastdfsTokenUtil.getToken(substring);
            if (StringUtils.isNotEmpty(token)) {
                tblStoreCatgResp.setLogo(showUrl + tblStoreCatgResp.getLogo() + "?" + token);
            }
            else {
                tblStoreCatgResp.setLogo(showUrl + tblStoreCatgResp.getLogo());
            }
        }
    }

    /**
     * @Author wangzhongbao
     * @Description 递归获取子节点
     * @Date 19:24 2019/4/29
     * @Param [rootCatgList, tblStoreCatgList]
     * @return void
     **/
    private void appendGoodsCatChildren(List<TblStoreCatgResp> rootCatgList, List<TblStoreCatgResp> tblStoreCatgList) {
        log.info("TblStoreCatgManager appendGoodsCatChildren start");

        Iterator<TblStoreCatgResp> iter = tblStoreCatgList.iterator();
        if (CollectionUtils.isNotEmpty(rootCatgList) && iter.hasNext()) {
            for (TblStoreCatgResp parentNode : rootCatgList) {
                List<TblStoreCatgResp> childrenList = new ArrayList<>();
                iter = tblStoreCatgList.iterator();
                while (iter.hasNext()) {
                    TblStoreCatgResp prodGoodsCat = iter.next();
                    if (parentNode.getCatId().equals(prodGoodsCat.getParentId())) {
                        TblStoreCatgResp childrenNode = new TblStoreCatgResp();
                        BeanUtils.copyProperties(prodGoodsCat, childrenNode);
                        childrenList.add(childrenNode);
                        iter.remove();
                    }
                }
                parentNode.setChildren(childrenList);
                appendGoodsCatChildren(childrenList, tblStoreCatgList);
            }
        }
        log.info("TblStoreCatgManager appendGoodsCatChildren end");
    }

    public TblStoreCatgResp qryStoreCatgDetail(String catgId) throws BaseException {
        log.info("TblStoreCatgManager qryStoreCatgDetail start, catgId = [{}]", catgId);

        TblStoreCatg tblStoreCatg = tblStoreCatgMapper.selectById(catgId);
        TblStoreCatgResp resp = new TblStoreCatgResp();
        BeanUtils.copyProperties(tblStoreCatg, resp);
        getRealLogo(resp);

        log.info("TblStoreCatgManager appendGoodsCatChildren end");
        return resp;
    }

    public void updateSyncStateByCatId(String catId, String syncState){
        tblStoreCatgMapper.updateSyncStateByCatId(catId, syncState);
    }
}
