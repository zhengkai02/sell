package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.HttpSessionUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.ColumnNameDef;
import com.iwhalecloud.retail.offer.consts.CommonStateDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.ContactChannelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryContactChannelReq;
import com.iwhalecloud.retail.offer.dto.resp.ContactChannelResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryContactChannelResp;
import com.iwhalecloud.retail.offer.entity.ChannelType;
import com.iwhalecloud.retail.offer.entity.ContactChannel;
import com.iwhalecloud.retail.offer.mapper.ChannelTypeMapper;
import com.iwhalecloud.retail.offer.mapper.ContactChannelMapper;
import com.iwhalecloud.retail.offer.utils.DBDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContactChannelManager {

    @Autowired
    private ContactChannelMapper contactChannelMapper;

    @Autowired
    private ChannelTypeMapper channelTypeMapper;

    /**
     * 查询渠道
     * 
     * @author fanxiaofei
     * @return List<ContactChannel>
     */
    public List<ContactChannelResp> listContactChannel() {
        log.info("CommonManager listContactChannel start");
        List<ContactChannelResp> result = contactChannelMapper.listContactChannelName();
        if (null == result) {
            result = new ArrayList<>();
        }
        log.info("CommonManager listContactChannel end");
        return result;
    }

    public ArrayList<ContactChannelResp> queryContactChannelAll() {
        log.info("ContactChannelManager queryContactChannelAll start");
        ArrayList<ContactChannelResp> result = contactChannelMapper.queryContactChannelAll();
        if (null == result) {
            result = new ArrayList<>();
        }
        log.info("ContactChannelManager queryContactChannelAll end");
        return result;
    }

    /**
     * 查询渠道类型（域表）
     * 
     * @author huminghang
     * @return List<ChannelType>
     */
    public List<ChannelType> qryChannelTypeList() {
        log.info("ContactChannelManager qryChannelTypeList start");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
        List<ChannelType> result = channelTypeMapper.selectList(wrapper);
        log.info("ContactChannelManager qryChannelTypeList end");
        return result;
    }

    /**
     * 根据条件查询渠道
     *
     * @author huminghang
     * @return List<ContactChannel>
     */
    public List<ContactChannelResp> qryContactChannelList(ContactChannelReq req) {
        log.info("ContactChannelManager qryContactChannelList start");
        List<ContactChannelResp> result = contactChannelMapper.qryContactChannelList(req);
        log.info("ContactChannelManager qryContactChannelList end");
        return result;
    }

    /**
     * 根据条件分页查询渠道
     *
     * @author ji.maowei
     * @return Page<ContactChannel>
     */
    public Page<ContactChannelResp> qryContactChannelPage(ContactChannelReq req) {
        log.info("ContactChannelManager qryContactChannelPage start");
        Page<ContactChannelResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        page = contactChannelMapper.qryContactChannelPage(page, req);
        log.info("ContactChannelManager qryContactChannelPage end");
        return page;
    }

    public ContactChannel qryContactChannel(String contactChannelId) {
        log.info("ContactChannelManager qryContactChannel start, contactChannelId = [{}]", contactChannelId);
        ContactChannel contactChannel = contactChannelMapper.selectById(contactChannelId);
        log.info("ContactChannelManager qryContactChannel end");
        return contactChannel;
    }

    public void addContactChannel(ContactChannel req) throws BaseException {
        log.info("ContactChannelManager addContactChannel start, request = [{}]", req);

        AssertUtil.isNotEmpty(req.getOrgId(), OfferBaseMessageCodeEnum.ORG_ID_IS_NULL);
        AssertUtil.isNotEmpty(req.getContactChannelName(), OfferBaseMessageCodeEnum.CHANNEL_NAME_IS_NULL);
        // 渠道名称不能重复
        checkContactChannelName(req.getContactChannelName());
        AssertUtil.isNotNull(req.getChannelType(), OfferBaseMessageCodeEnum.CHANNEL_TYPE_IS_NULL);
        if (StringUtils.isNotEmpty(req.getChannelCode())) {
            Map<String, Object> qryMap = new HashMap<>();
            qryMap.put(ColumnNameDef.CHANNEL_CODE, req.getChannelCode());
            qryMap.put(ColumnNameDef.STATE, CommonStateDef.ACTIVE);
            List<ContactChannel> contactChannels = contactChannelMapper.selectByMap(qryMap);

            if (CollectionUtils.isNotEmpty(contactChannels)) {
                throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_CODE_HAS_EXIST);
            }
        }
        UserDTO userDTO = HttpSessionUtil.get();
        String userId = "";
        if (null != userDTO) {
            userId = userDTO.getUserId().toString();
        }

        req.setContactChannelId(UidGeneator.getUIDStr());
        req.setCreateBy(userId);
        req.setCreateTime(DBDateUtil.getCurrentDBDateTime());
        req.setModifyBy(userId);
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        req.setState(CommonStateDef.ACTIVE);
        contactChannelMapper.insert(req);
        log.info("ContactChannelManager addContactChannel end");
    }

    public void modContactChannel(ContactChannel req) throws BaseException {
        log.info("ContactChannelManager modContactChannel start, request = [{}]", req);

        AssertUtil.isNotEmpty(req.getContactChannelName(), OfferBaseMessageCodeEnum.CHANNEL_NAME_IS_NULL);
        // 渠道名称不能重复
        checkContactChannelName(req.getContactChannelName());
        UserDTO userDTO = HttpSessionUtil.get();
        String userId = "";
        if (null != userDTO) {
            userId = userDTO.getUserId().toString();
        }
        req.setModifyBy(userId);
        req.setModifyTime(DBDateUtil.getCurrentDBDateTime());
        contactChannelMapper.updateById(req);
        log.info("ContactChannelManager modContactChannel end");
    }

    /**
     * 根据渠道id查询渠道
     * 
     * @param req QueryContactChannelReq
     * @return QueryContactChannelResp
     */
    public QueryContactChannelResp qryByChannelId(QueryContactChannelReq req) throws BaseException {
        log.info("ContactChannelManager qryByChannelId start, req = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getContactChannelId(), OfferBaseMessageCodeEnum.REQUEST_IS_NULL);

        QueryContactChannelResp result = contactChannelMapper
            .qryContactChannelByContactChannelId(req.getContactChannelId());
        AssertUtil.isNotNull(result, OfferBaseMessageCodeEnum.CHANNEL_IS_NULL);

        log.info("ContactChannelManager qryByChannelId end");
        return result;
    }

    /**
     * 渠道名称重复检验
     */
    private void checkContactChannelName(String contactChannelName) throws BaseException {
        List<ContactChannelResp> contactChannelRespList = contactChannelMapper.listContactChannelName();
        if (CollectionUtils.isNotEmpty(contactChannelRespList)) {
            Iterator<ContactChannelResp> iter = contactChannelRespList.iterator();
            while (iter.hasNext()) {
                ContactChannelResp contactChannelResp = iter.next();
                if (contactChannelResp.getContactChannelName().equals(contactChannelName)) {
                    throw new BaseException(OfferBaseMessageCodeEnum.CHANNEL_NAME_HAS_EXIST);
                }
            }
        }
    }
}
