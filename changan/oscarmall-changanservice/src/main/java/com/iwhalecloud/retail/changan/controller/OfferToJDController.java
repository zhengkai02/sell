package com.iwhalecloud.retail.changan.controller;

import com.iwhalecloud.retail.changan.DTO.MultiRecordsDTO;
import com.iwhalecloud.retail.changan.DTO.OfferToJDRequestDTO;
import com.iwhalecloud.retail.changan.VO.OfferToJDResultVO;
import com.iwhalecloud.retail.changan.service.OfferToJDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @auther add by wanghw on 2019/11/17
 * @return
 */
@RestController
@RequestMapping("/JieDa/api")
@Slf4j
public class OfferToJDController {
    @Autowired
    private OfferToJDService offerToJDService;

    /**
     * 商品同步接口
     * @return
     */
    @PostMapping("/offer")
    public OfferToJDResultVO SyncOfferToJD(@RequestBody OfferToJDRequestDTO offerToJDRequestDTO) {
        log.info("OfferToJDController SyncOfferToJD start, App = [{}]", offerToJDRequestDTO);
        OfferToJDResultVO result = offerToJDService.syncOfferToJD(offerToJDRequestDTO);
        log.info("OfferToJDController SyncOfferToJD end, result = [{}]", result);
        return result;
    }
    /**
     *  通用通骏接口2.7
     * @author wanghw
     * @return
     */
    @PostMapping("/common")
    public OfferToJDResultVO commonNotification(MultiRecordsDTO multiDTO) {
        log.info("OfferToJDController MultiRecordsDTO start, App = [{}]", multiDTO);
        OfferToJDResultVO result = offerToJDService.commonNotification(multiDTO);
        log.info("OfferToJDController MultiRecordsDTO end, result = [{}]", result);
        return result;
    }

}
