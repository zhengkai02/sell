package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.BatchAuditEvaluationReq;
import com.iwhalecloud.retail.offer.dto.req.ContentEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationModeReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.EvaluationReq;
import com.iwhalecloud.retail.offer.dto.req.ProdGoodsEvaluationQryReq;
import com.iwhalecloud.retail.offer.dto.req.QueryEvaluationByUserIdAndObjReq;
import com.iwhalecloud.retail.offer.dto.resp.ContentEvaluationQryResp;
import com.iwhalecloud.retail.offer.dto.resp.EvaluationResp;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsEvaluationQryResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwhalecloud.retail.offer.manager.EvaluationManager;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.controller.microservice <br>
 * @since V9.0<br>
 */
@Api(tags = "评论")
@Slf4j
@RestController
@RequestMapping("/offer/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationManager evaluationManager;

    @PostMapping("/create")
    public ResultVO addEvaluation(@RequestBody EvaluationReq req) {
        log.info("EvaluationController addEvaluation start");
        ResultVO result = ResultVOCheckUtil.buildResultVONoReturn(evaluationManager::addEvaluation, req);
        log.info("EvaluationController addEvaluation end");
        return result;
    }

    @PostMapping("/query")
    public Page<EvaluationResp> qryEvaluation(@RequestBody EvaluationQryReq req) throws BaseException {
        log.info("EvaluationController qryEvaluation start");
        Page<EvaluationResp> result = evaluationManager.qryEvaluation(req);
        log.info("EvaluationController qryEvaluation end");
        return result;
    }

    @PostMapping("/prodgoods/query")
    public Page<ProdGoodsEvaluationQryResp> qryProdGoodsEvaluation(@RequestBody ProdGoodsEvaluationQryReq req) throws BaseException {
        log.info("EvaluationController qryProdGoodsEvaluation start");
        Page<ProdGoodsEvaluationQryResp> result = evaluationManager.qryProdGoodsEvaluation(req);
        log.info("EvaluationController qryProdGoodsEvaluation end");
        return result;
    }

    @PostMapping("/content/query")
    public Page<ContentEvaluationQryResp> qryContentEvaluation(@RequestBody ContentEvaluationQryReq req) throws BaseException {
        log.info("EvaluationController qryContentEvaluation start");
        Page<ContentEvaluationQryResp> result = evaluationManager.qryContentEvaluation(req);
        log.info("EvaluationController qryContentEvaluation end");
        return result;
    }

    @PostMapping("/batchdel")
    public int batchDelete(@RequestBody List<String> ids) throws BaseException {
        return evaluationManager.batchDelEvaluation(ids);
    }

    @PostMapping("/batchhide")
    public int batchHide(@RequestBody List<String> ids) throws BaseException {
        log.info("EvaluationController batchHide start");
        int result = evaluationManager.batchHide(ids);
        log.info("EvaluationController batchHide end");
        return result;
    }

    @PostMapping("/batchdisplay")
    public int batchDisplay(@RequestBody List<String> ids) throws BaseException {
        return evaluationManager.batchDisplay(ids);
    }

    @PostMapping("/mod")
    public int modEvaluation(@RequestBody EvaluationModeReq req) throws BaseException {
        return evaluationManager.modEvaluation(req);
    }


    @ApiOperation(value = "根据用户id和obj查询评价")
    @PostMapping("/obj/query")
    public ResultVO<EvaluationResp> qryEvaluationByObjAndUserId(@RequestBody QueryEvaluationByUserIdAndObjReq req) {
        log.info("EvaluationController qryEvaluationByObjAndUserId start");
        ResultVO<EvaluationResp> result = ResultVOCheckUtil.buildResultVO(evaluationManager::qryEvaluationByObjAndUserId, req);
        log.info("EvaluationController qryEvaluationByObjAndUserId end");
        return result;
    }


    /**
     * 批量审核
     * @author fanxiaofei
     * @date 2019-06-01
     */
    @PostMapping("/batchaudit")
    public ResultVO batchAudit(@RequestBody BatchAuditEvaluationReq req, HttpServletRequest request) throws BaseException {
        log.info("EvaluationController batchAudit start");
        ResultVO result = new ResultVO<>();
        try {
            evaluationManager.batchAudit(req, request);
            result.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            result.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
        }
        catch (BaseException e) {
            log.error("BaseException = [{}]", e);
            result.setMessage(e.getDesc());
            result.setCode(e.getCode());
        }
        catch (Exception e) {
            log.error("Exception = [{}]", e);
            result.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
            result.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
        }
        log.info("EvaluationController batchAudit end");
        return result;
    }

}
