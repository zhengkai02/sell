package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.AreaDTO;
import com.iwhalecloud.retail.offer.dto.req.AreaReq;
import com.iwhalecloud.retail.offer.mapper.AreaMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/24 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since CRM <br>
 */
@Component
@Slf4j
public class AreaManager {

    @Autowired
    private AreaMapper areaMapper;

    public ArrayList<AreaDTO> qryAreaListByLevel(AreaReq areaReq) throws BaseException {
        log.info("begin to qryAreaListByLevel, areaReq = [{}]", areaReq);
        AssertUtil.isNotNull(areaReq, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(areaReq.getLevel(), OfferBaseMessageCodeEnum.AREA_LEVEL_IS_NULL);
        List<AreaDTO> resultList = areaMapper.selectListByLevel(areaReq.getLevel());
        log.info("end to qryAreaListByLevel, resultList = [{}]", resultList);
        return (ArrayList<AreaDTO>) resultList;
    }

    public ArrayList<AreaDTO> qryAreaListByParent(AreaReq areaReq) throws BaseException {
        log.info("begin to qryAreaListByLevel, areaReq = [{}]", areaReq);
        AssertUtil.isNotNull(areaReq, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotNull(areaReq.getParentCode(), OfferBaseMessageCodeEnum.AREA_PARENT_CODE_IS_NULL);
        List<AreaDTO> resultList = areaMapper.selectListByParentCode(areaReq.getParentCode());
        log.info("end to qryAreaListByLevel, resultList = [{}]", resultList);
        return (ArrayList<AreaDTO>) resultList;
    }
}
