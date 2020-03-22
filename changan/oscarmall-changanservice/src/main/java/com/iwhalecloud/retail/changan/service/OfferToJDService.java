package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.DTO.MultiRecordsDTO;
import com.iwhalecloud.retail.changan.DTO.OfferToJDRequestDTO;
import com.iwhalecloud.retail.changan.VO.OfferToJDResultVO;

public interface OfferToJDService {
    /**
     * 商品同步到捷达接口2.8
     * @author wanghw
     * @data 2019-11-17
     */
    OfferToJDResultVO syncOfferToJD(OfferToJDRequestDTO offerToJDRequestDTO);

    /**
     * 通用通骏接口2.7
     * @author wanghw
     * @data 2019-11-18
     */
    OfferToJDResultVO commonNotification(MultiRecordsDTO multiRecordsDTO);

}
