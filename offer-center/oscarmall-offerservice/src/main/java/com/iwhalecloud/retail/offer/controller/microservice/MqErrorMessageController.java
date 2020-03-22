package com.iwhalecloud.retail.offer.controller.microservice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.entity.TblMqErrorMessage;
import com.iwhalecloud.retail.offer.manager.MqErrorMessageManager;
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
@Api(tags = "mq错误消息")
@RestController
@RequestMapping("/offer/errormessage")
@Slf4j
public class MqErrorMessageController {

    @Autowired
    private MqErrorMessageManager mqErrorMessageManager;

    @PostMapping("/insert")
    public ResultVO insertErrorMessage(@RequestBody TblMqErrorMessage tblMqErrorMessage) {
        log.info("MqErrorMessageController insertErrorMessage start, tblMqErrorMessage = [{}]", tblMqErrorMessage);
        ResultVO resultVO = ResultVOCheckUtil.buildResultVONoReturn(mqErrorMessageManager::insertErrorMessage, tblMqErrorMessage);
        log.info("MqErrorMessageController insertErrorMessage end");
        return resultVO;
    }

    @PostMapping("/finish/{messageId}")
    public ResultVO finishErrorMessage(@ApiParam(value = "消息ID") @PathVariable String messageId) {
        log.info("MqErrorMessageController finishErrorMessage start, messageId = [{}]", messageId);
        ResultVO resultVO = ResultVOCheckUtil.buildResultVONoReturn(mqErrorMessageManager::finishErrorMessage, messageId);
        log.info("MqErrorMessageController insertErrorMessage end");
        return resultVO;
    }
}
