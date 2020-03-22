package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.PageEvaluationLogReq;
import com.iwhalecloud.retail.offer.dto.resp.PageEvaluationLogResp;
import com.iwhalecloud.retail.offer.manager.EvaluationLogManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 评价计算日志
 * @author fanxiaofei
 * @date 2019-06-02
 */
@Api(tags = "评价计算日志")
@Slf4j
@RestController
@RequestMapping("/offer/evaluation/log")
public class EvaluationLogController {

    @Autowired
    private EvaluationLogManager evaluationLogManager;

    @ApiOperation(value = "查询评价计算日志")
    @PostMapping(value = "/page")
    public ResultVO<Page<PageEvaluationLogResp>> page(@RequestBody PageEvaluationLogReq req) {
        log.info("EvaluationLogController page start");
        ResultVO<Page<PageEvaluationLogResp>> result = ResultVOCheckUtil.buildResultVO(evaluationLogManager::pageEvaluationLog, req);
        log.info("EvaluationLogController page end");
        return result;
    }

}
