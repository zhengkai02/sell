package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.entity.SystemParam;
import com.iwhalecloud.retail.offer.manager.SystemParamManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统参数")
@Slf4j
@RestController
@RequestMapping("/offer/sysparam")
public class SystemParamController {

    @Autowired
    private SystemParamManager systemParamManager;

    @PostMapping("/{mask}")
    @ResponseStatus(value = HttpStatus.OK)
    public SystemParam selectSystemParamByMask(@ApiParam(value = "MASK") @PathVariable String mask) {
        log.info("SystemParamController selectSystemParamByMask start");
        SystemParam systemParam = systemParamManager.selectSystemParamByMask(mask);
        log.info("SystemParamController selectSystemParamByMask end");
        return systemParam;
    }
}
