package com.iwhalecloud.retail.offer.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.consts.AbstractEvaluationLogDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.req.ContentBaseQryReq;
import com.iwhalecloud.retail.offer.dto.req.PageEvaluationLogReq;
import com.iwhalecloud.retail.offer.dto.resp.PageEvaluationLogResp;
import com.iwhalecloud.retail.offer.mapper.EvaluationLogMapper;
import com.iwhalecloud.retail.offer.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 评价计算日志
 * @author fanxiaofei
 * @date 2019/06/02
 */
@Slf4j
@Component
public class EvaluationLogManager {


    @Autowired
    private EvaluationLogMapper evaluationLogMapper;

    @Autowired
    private ContentService contentService;


    /**
     * 评价计算日志分页
     * @param req PageEvaluationLogReq
     * @return Page<PageEvaluationLogResp>
     */
    public Page<PageEvaluationLogResp> pageEvaluationLog(PageEvaluationLogReq req) throws BaseException {
        log.info("EvaluationLogManager pageEvaluationLog start, PageEvaluationLogReq = [{}]", req);

        AssertUtil.isNotNull(req, OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        AssertUtil.isNotNull(req.getObjType(), OfferBaseMessageCodeEnum.EVALUATION_OBJ_TYPE_IS_NULL);
        if (!AbstractEvaluationLogDef.OBJ_TYPE_A.equals(req.getObjType()) &&
            !AbstractEvaluationLogDef.OBJ_TYPE_B.equals(req.getObjType())) {
            throw new BaseException(OfferBaseMessageCodeEnum.EVALUATION_OBJ_TYPE_IS_ERROR);
        }
        AssertUtil.isNotNull(req.getObjId(), OfferBaseMessageCodeEnum.EVALUATION_OBJ_ID_IS_NULL);

        Page<PageEvaluationLogResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<PageEvaluationLogResp> result = evaluationLogMapper.pageEvaluationLog(page, req);

        //对结果进行重新填充name字段，objType为B的话，需要填充content中心的content_base的title字段
        List<String> contentIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result.getRecords())) {
            for (PageEvaluationLogResp resp : result.getRecords()) {
                if (AbstractEvaluationLogDef.OBJ_TYPE_B.equals(resp.getObjType())) {
                    if (!contentIds.contains(resp.getObjId())) {
                        contentIds.add(resp.getObjId());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(contentIds)) {
            ContentBaseQryReq requset = new ContentBaseQryReq();
            requset.setContentIds(contentIds);
            ResultVO<ArrayList<ContentBaseDTO>> contentBaseDtos = contentService.selectContentBaseListByCond(requset);
            ResultVOCheckUtil.checkResultVO(contentBaseDtos);
            for (PageEvaluationLogResp resp : result.getRecords()) {
                for (ContentBaseDTO contentBaseDTO : contentBaseDtos.getData()) {
                    if (resp.getObjId().equals(contentBaseDTO.getContentId().toString())) {
                        resp.setName(contentBaseDTO.getTitle());
                    }
                }
            }
        }

        log.info("EvaluationLogManager pageEvaluationLog end");
        return result;
    }

}
