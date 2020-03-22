package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.offer.dto.req.DomainReq;
import com.iwhalecloud.retail.offer.entity.Domain;
import com.iwhalecloud.retail.offer.manager.CommonManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * <Description> 共用controller <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 */
@Api(tags = "共用controller")
@Slf4j
@RestController
@RequestMapping("/offer/common")
public class CommonController {
    
    @Autowired
    private CommonManager commonManager;
    
    @PostMapping("/list")
    public List<Domain> qryDomainInfo(@RequestBody DomainReq req) {
        log.info("CommonWebappController qryDomainInfo start");
        List<Domain> result = commonManager.qryDomainInfo(req);
        log.info("CommonWebappController qryDomainInfo end");
        return result;
    }

}
