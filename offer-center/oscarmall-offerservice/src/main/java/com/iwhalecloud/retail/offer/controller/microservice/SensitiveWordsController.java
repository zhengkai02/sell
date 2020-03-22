package com.iwhalecloud.retail.offer.controller.microservice;

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
import com.iwhalecloud.retail.offer.dto.req.AddSensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.req.ModSensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.req.QrySensitiveWordsReq;
import com.iwhalecloud.retail.offer.dto.resp.AddSensitiveWordsResp;
import com.iwhalecloud.retail.offer.dto.resp.ModSensitiveWordsResp;
import com.iwhalecloud.retail.offer.entity.SensitiveWords;
import com.iwhalecloud.retail.offer.manager.SensitiveWordsManager;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
@Api(tags = "敏感词")
@Slf4j
@RestController
@RequestMapping("/offer/sensitivewords")
public class SensitiveWordsController {

    @Autowired
    private SensitiveWordsManager sensitiveWordsManager;

    @ApiOperation(value = "查询所有的敏感词")
    @PostMapping("/list")
    public Page<SensitiveWords> qryAllSensitiveWords(@RequestBody QrySensitiveWordsReq request) {
        log.info("SensitiveWordsController qryAllSensitiveWords start");
        Page<SensitiveWords> resp = sensitiveWordsManager.qryAllSensitiveWords(request);
        log.info("SensitiveWordsController qryAllSensitiveWords end");
        return resp;
    }

    @ApiOperation(value = "添加敏感词")
    @PostMapping("/{userId}/add")
    public AddSensitiveWordsResp addSensitiveWords(@RequestBody AddSensitiveWordsReq request, @ApiParam(value = "用户ID") @PathVariable Long userId) throws BaseException {
        log.info("SensitiveWordsController addSensitiveWords request = [{}]", request);
        AddSensitiveWordsResp resp = sensitiveWordsManager.addSensitiveWords(request, userId);
        log.info("SensitiveWordsController addSensitiveWords end");
        return resp;
    }

    @ApiOperation(value = "修改敏感词")
    @PostMapping("/{userId}/mod/{wordId}")
    public ModSensitiveWordsResp modifySensitiveWords(@ApiParam(value = "敏感词ID") @PathVariable(value = "wordId") Long wordId,
                                                      @RequestBody ModSensitiveWordsReq request, @ApiParam(value = "用户ID") @PathVariable(value = "userId") Long userId) throws BaseException {
        log.info("SensitiveWordsController modifySensitiveWords start request = [{}]", request);
        ModSensitiveWordsResp resp = sensitiveWordsManager.modifySensitiveWords(wordId, request, userId);
        log.info("SensitiveWordsController modifySensitiveWords end");
        return resp;
    }

    @ApiOperation(value = "删除敏感词")
    @PostMapping("/{userId}/delete/{wordId}")
    public void deleteSensitiveWords(@PathVariable(value = "wordId") Long wordId, @ApiParam(value = "用户ID") @PathVariable(value = "userId") Long userId) throws BaseException {
        log.info("SensitiveWordsController deleteSensitiveWords start wordId = ", wordId);
        sensitiveWordsManager.deleteSensitiveWords(wordId, userId);
        log.info("SensitiveWordsController deleteSensitiveWords end");
    }

}
