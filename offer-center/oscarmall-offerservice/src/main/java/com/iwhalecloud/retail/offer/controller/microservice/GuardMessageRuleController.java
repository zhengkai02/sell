package com.iwhalecloud.retail.offer.controller.microservice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwhalecloud.retail.common.dto.GuardMessageRule;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.manager.GuardMessageRuleManager;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/7 <br>
 * @see com.iwhalecloud.retail.offer.controller.microservice <br>
 * @since V9.0C<br>
 */
@Api(tags = "异常消息规则")
@RestController
@RequestMapping("/offer/messagerule")
@Slf4j
public class GuardMessageRuleController {

    @Autowired
    private GuardMessageRuleManager guardMessageRuleManager;

    @PostMapping("/{queue}")
    public ResultVO<GuardMessageRule> qryGuardMessageRuleByQueue(@ApiParam(value = "队列") @PathVariable String queue) {
        log.info("GuardMessageRuleController qryGuardMessageRuleByTopic start");
        ResultVO<GuardMessageRule> result = ResultVOCheckUtil.buildResultVO(guardMessageRuleManager::qryGuardMessageRuleByQueue, queue);
        log.info("GuardMessageRuleController qryGuardMessageRuleByTopic end");
        return result;
    }
}
