package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.entity.Domain;
import com.iwhalecloud.retail.offer.mapper.DomainMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "domain")
@RestController("domainMicroService")
@RequestMapping("/offer/domain")
@Slf4j
public class DomainController {

    @Autowired
    private DomainMapper domainMapper;

    @PostMapping("/qry")
    public List<Domain> select(@RequestBody(required = false) Domain request) throws BaseException {
        log.info("DomainController.select start. req=[{}]", request);
        if (null == request) {
            request = new Domain();
        }

        List<Domain> result = domainMapper.select(request);

        log.info("DomainController.select end. req=[{}]", result);
        return result;
    }
}
