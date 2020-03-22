package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.AttrDelReq;
import com.iwhalecloud.retail.offer.dto.req.QryAttrReq;
import com.iwhalecloud.retail.offer.dto.req.QryGoodsAttrListReq;
import com.iwhalecloud.retail.offer.dto.req.QueryAttrListReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrReq;
import com.iwhalecloud.retail.offer.dto.resp.AttrResp;
import com.iwhalecloud.retail.offer.dto.resp.GoodsCatAttrResp;
import com.iwhalecloud.retail.offer.entity.Attr;
import com.iwhalecloud.retail.offer.entity.AttrValue;
import com.iwhalecloud.retail.offer.mapper.AttrMapper;
import com.iwhalecloud.retail.offer.mapper.AttrValueMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AttrManager {

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    /**
     * 查询所有属性
     * 
     * @author fanxiaofei
     * @return List<AttrResp>
     */
    public Page<AttrResp> qryAttrList(QryAttrReq request) throws BaseException {
        log.info("AttrManager qryAttrList start request = [{}]", request);
        if (null == request || null == request.getPageNo() || null == request.getPageSize()) {
            throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
        }
        Page<Attr> page = new Page<>(request.getPageNo(), request.getPageSize());
        Page<Attr> list = attrMapper.queryAttrList(page, request);
        List<AttrResp> result = buildAttrResp(list.getRecords());
        Page<AttrResp> resp = new Page<>();
        resp.setRecords(result);
        resp.setCurrent(list.getCurrent());
        resp.setSize(list.getSize());
        resp.setTotal(list.getTotal());
        resp.setPages(list.getPages());
        log.info("AttrManager qryAttrList end");
        return resp;
    }

    /**
     * 共用封装 List<AttrResp>
     * 
     * @param list List<Attr>
     * @return List<AttrResp>
     */
    private List<AttrResp> buildAttrResp(List<Attr> list) {
        List<AttrResp> result = new ArrayList<>();
        list.forEach(attr -> {
            AttrResp attrResp = new AttrResp();
            BeanUtils.copyProperties(attr, attrResp);
            List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(attr.getAttrId());
            attrResp.setAttrValueList(attrValues);
            result.add(attrResp);
        });
        return result;
    }

    /**
     * 根据商品id过滤当前商品已有属性的其他所有属性
     * 
     * @author fanxiaofei
     * @return List<AttrResp>
     */
    public List<AttrResp> qryOutAttrListByGoodsId(String goodsId) {
        log.info("AttrManager qryOutAttrListByGoodsId start goodsId = {[]}", goodsId);
        List<Attr> list = attrMapper.queryOutAttrListByGoodsId(goodsId);
        List<AttrResp> result = buildAttrResp(list);
        log.info("AttrManager qryOutAttrListByGoodsId end");
        return result;
    }

    /**
     * 根据目录id,商品id,sku三者过滤查询属性
     * 
     * @param req QryGoodsAttrListReq
     * @return List<GoodsCatAttrResp>
     */
    public List<GoodsCatAttrResp> qryAttrListByFilter(QryGoodsAttrListReq req) {
        log.info("AttrManager qryAttrListByFilter start, req = [{}]", req);

        List<GoodsCatAttrResp> goodsCatAttrResps = attrMapper.qryAttrListByFilter(req);

        if (CollectionUtils.isNotEmpty(goodsCatAttrResps)) {
            goodsCatAttrResps.forEach(prodGoodsCatAttrResp -> {
                List<AttrValue> attrValues = attrValueMapper.queryAttrValueByAttrId(prodGoodsCatAttrResp.getAttrId());
                prodGoodsCatAttrResp.setAttrValueList(attrValues);
            });
        }

        log.info("AttrManager qryAttrListByFilter end");
        return goodsCatAttrResps;
    }

    /**
     * 新增属性
     * 
     * @author huminghang
     * @return AttrResp
     */
    public AttrResp addAttr(AttrReq request) throws BaseException {
        log.info("AttrManager addAttr start, AttrReq = [{}]", request);

        // 校验属性编码
        Map<String, Object> qryMap = new HashMap<>(1);
        qryMap.put("attr_code", request.getAttrCode());
        qryMap.put("state", CommonStateDef.ACTIVE);
        List<Attr> checkAttr = attrMapper.selectByMap(qryMap);
        if (CollectionUtils.isNotEmpty(checkAttr)) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_CODE_EXIST);
        }
        // 属性值需要唯一
        List<AttrValue> attrValues = request.getAttrValueList();
        if (CollectionUtils.isEmpty(attrValues)) {
            Set<String> valueSet = new HashSet<>(attrValues.size());
            attrValues.forEach(attrValue -> valueSet.add(attrValue.getValue()));
            if (valueSet.size() != attrValues.size()) {
                throw new BaseException(OfferBaseMessageCodeEnum.ATTR_VALUE_IS_SAME);
            }
        }

        // 新增属性
        Attr attr = new Attr();
        BeanUtils.copyProperties(request, attr);
        attr.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        attr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        UserDTO userDTO = HttpSessionUtil.get();
        if (userDTO != null) {
            attr.setCreateBy(userDTO.getUserId().toString());
            attr.setModifyBy(userDTO.getUserId().toString());
        }
        attr.setState(CommonStateDef.ACTIVE);
        attr.setStateDate(DBDateUtil.getCurrentDBDateTime());
        attr.setAttrId(UidGeneator.getUIDStr());
        attrMapper.insert(attr);

        // 新增属性值
        if (CollectionUtils.isNotEmpty(attrValues)) {
            attrValues.forEach(attrValue -> {
                attrValue.setAttrValueId(UidGeneator.getUIDStr());
                attrValue.setAttrId(attr.getAttrId());
                attrValue.setCreateBy(attr.getCreateBy());
                attrValue.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                attrValue.setModifyBy(attr.getCreateBy());
                attrValue.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                attrValue.setState(CommonStateDef.ACTIVE);
                attrValueMapper.insert(attrValue);
            });
        }

        AttrResp result = new AttrResp();
        BeanUtils.copyProperties(attr, result);
        result.setAttrValueList(attrValues);
        log.info("AttrManager addAttr end");
        return result;
    }

    /**
     * 修改属性
     * 
     * @author huminghang
     * @return AttrResp
     */
    public AttrResp modAttr(String attrId, AttrReq request) throws BaseException {
        log.info("AttrManager modAttr start, attrId = [{}], AttrReq = [{}]", attrId, request);

        // 校验属性编码
        Map<String, Object> qryMap = new HashMap<>();
        qryMap.put("attr_code", request.getAttrCode());
        qryMap.put("state", CommonStateDef.ACTIVE);
        List<Attr> checkAttr = attrMapper.selectByMap(qryMap);
        if (CollectionUtils.isNotEmpty(checkAttr) && checkAttr.size() > 1) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_CODE_EXIST);
        }
        // 属性值需要唯一
        List<AttrValue> attrValues = request.getAttrValueList();
        if (CollectionUtils.isNotEmpty(attrValues)) {
            Set<String> valueSet = new HashSet<>(attrValues.size());
            attrValues.forEach(attrValue -> valueSet.add(attrValue.getValue()));
            if (valueSet.size() != attrValues.size()) {
                throw new BaseException(OfferBaseMessageCodeEnum.ATTR_VALUE_IS_SAME);
            }
        }

        // 修改属性
        Attr attr = new Attr();
        BeanUtils.copyProperties(request, attr);
        attr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        UserDTO userDTO = HttpSessionUtil.get();
        if (userDTO != null) {
            attr.setModifyBy(userDTO.getUserId().toString());
        }
        attr.setAttrId(attrId);
        attrMapper.updateById(attr);
        // 修改属性值 先删除后新增
        attrValueMapper.delAttrValueByAttrId(attrId, attr.getModifyBy());
        if (CollectionUtils.isNotEmpty(attrValues)) {
            for (AttrValue attrValue : attrValues) {
                attrValue.setAttrValueId(UidGeneator.getUIDStr());
                attrValue.setCreateTime(DBDateUtil.getCurrentDBDateTime());
                attrValue.setCreateBy(attr.getModifyBy());
                attrValue.setModifyTime(DBDateUtil.getCurrentDBDateTime());
                attrValue.setModifyBy(attr.getModifyBy());
                attrValue.setState(CommonStateDef.ACTIVE);
                attrValueMapper.insert(attrValue);
            }
        }
        AttrResp result = new AttrResp();
        BeanUtils.copyProperties(attr, result);
        result.setAttrValueList(attrValues);
        log.info("AttrManager modAttr end");
        return result;
    }

    /**
     * 删除属性
     * 
     * @param request
     * @throws BaseException
     */
    public void delAttr(AttrDelReq request) throws BaseException {
        AssertUtil.isNotNull(request.getAttrId(), OfferBaseMessageCodeEnum.ATTR_ID_IS_NULL);
        log.info("AttrManager delAttr start request = [{}]", request);
        Attr attr = checkAttrExist(request.getAttrId());
        if ((request.getUserDTO() != null) && (request.getUserDTO().getUserId() != null)) {
            attr.setModifyBy(request.getUserDTO().getUserId().toString());
        }
        attr.setState(CommonStateDef.INACTIVE);
        attr.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        attrMapper.updateById(attr);
        log.info("AttrManager delAttr end");
    }

    public Attr queryAttrById(String attrId) {
        log.info("AttrManager queryAttrById start, attrId = [{}]", attrId);
        Attr attr = attrMapper.selectById(attrId);
        log.info("AttrManager queryAttrById end");
        return attr;
    }

    public Attr queryAttrByCode(String attrCode) {
        log.info("AttrManager queryAttrByCode start, attrCode = [{}]", attrCode);
        Attr attr = attrMapper.selectByCode(attrCode);
        log.info("AttrManager queryAttrByCode end");
        return attr;
    }

    public Attr selectByCodeNoTenantId(String attrCode) {
        log.info("AttrManager selectByCodeNoTenantId start, attrCode = [{}]", attrCode);
        Attr attr = attrMapper.selectByCodeNoTenantId(attrCode);
        log.info("AttrManager selectByCodeNoTenantId end");
        return attr;
    }

    public List<AttrValue> queryAttrValueByAttrId(@PathVariable String attrId) {
        log.info("AttrManager queryAttrValueByAttrId start, attrId = [{}]", attrId);
        List<AttrValue> attrValueList = attrValueMapper.queryAttrValueByAttrId(attrId);
        if (null == attrValueList) {
            attrValueList = new ArrayList<>();
        }
        log.info("AttrManager queryAttrValueByAttrId end");
        return attrValueList;
    }

    private Attr checkAttrExist(String attrId) throws BaseException {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(ColumnNameDef.ATTR_ID, attrId);
        attrMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<Attr> attrs = attrMapper.selectByMap(attrMap);
        if (CollectionUtils.isEmpty(attrs)) {
            throw new BaseException(OfferBaseMessageCodeEnum.ATTR_ID_IS_NOT_EXIST);
        }
        else {
            return attrs.get(0);
        }
    }

    public ArrayList<AttrResp> qryAttrListByCond(QueryAttrListReq request) throws BaseException {
        log.info("AttrManager QueryAttrListReq start, requset = [{}]", request);
        List<Attr> list = attrMapper.queryAttrArray(request);
        List<AttrResp> attrResps = buildAttrResp(list);
        ArrayList<AttrResp> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(attrResps)) {
            log.info("AttrManager QueryAttrListReq end, result = [{}]", attrResps);
            return (ArrayList<AttrResp>) attrResps;
        }
        log.info("AttrManager QueryAttrListReq end, result = [{}]", result);
        return result;
    }

}
