package com.iwhalecloud.retail.offer.controller.microservice;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.req.QryErrorMqMessageReq;
import com.iwhalecloud.retail.offer.dto.resp.QryErrorMqMessageResp;
import com.iwhalecloud.retail.offer.manager.GuardMessageManager;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/9 <br>
 * @see com.iwhalecloud.retail.offer.controller.microservice <br>
 * @since V9.0C<br>
 */
@Api(tags = "异常消息")
@RestController
@Slf4j
@RequestMapping("/offer/guardmessage")
public class GuardMessageController {

    @Autowired
    private GuardMessageManager guardMessageManager;

    @PostMapping(value = "")
    public Page<QryErrorMqMessageResp> qryErrorMqMessage(@RequestBody QryErrorMqMessageReq req) {
        log.info("GuardMessageController qryErrorMqMessage start, req = [{}]", req);
        Page<QryErrorMqMessageResp> result = guardMessageManager.qryErrorMqMessage(req);

        log.info("GuardMessageController qryErrorMqMessage end, result = [{}]", result);
        return result;
    }

    @PostMapping(value = "/del/{messageId}")
    public int deleteErrorMqMessage(@ApiParam(value = "消息ID") @PathVariable String messageId) throws BaseException {
        log.info("GuardMessageController deleteErrorMqMessage start, messageId = [{}]", messageId);
        guardMessageManager.deleteErrorMqMessage(messageId);
        log.info("GuardMessageController deleteErrorMqMessage end");

        return 1;
    }

    @PostMapping(value = "/resend/{messageId}")
    public int resendMessage(@ApiParam(value = "消息ID") @PathVariable String messageId) throws BaseException {
        log.info("GuardMessageController resendMessage start, messageId = [{}]", messageId);
        guardMessageManager.resendMessage(messageId);
        log.info("GuardMessageController resendMessage end");
        return 1;
    }

    @PostMapping(value = "/queue")
    public List<String> qryQueueList() throws BaseException {
        log.info("GuardMessageController qryQueueList start");
        List<String> result = guardMessageManager.qryQueueList();
        log.info("GuardMessageController qryQueueList end, result = [{}]", result);
        return result;
    }

}
