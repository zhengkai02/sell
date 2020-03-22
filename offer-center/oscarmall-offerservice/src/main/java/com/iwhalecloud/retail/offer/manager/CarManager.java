package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.CarSeriesReq;
import com.iwhalecloud.retail.offer.dto.req.CarSpecReq;
import com.iwhalecloud.retail.offer.dto.resp.CarBrandResp;
import com.iwhalecloud.retail.offer.dto.resp.CarSeriesResp;
import com.iwhalecloud.retail.offer.dto.resp.CarSpecResp;
import com.iwhalecloud.retail.offer.mapper.CarBrandMapper;
import com.iwhalecloud.retail.offer.mapper.CarSeriesMapper;
import com.iwhalecloud.retail.offer.mapper.CarSpecMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/12 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since CRM <br>
 */
@Component
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarManager {

    @Autowired
    private CarBrandMapper carBrandMapper;

    @Autowired
    private CarSpecMapper carSpecMapper;

    @Autowired
    private CarSeriesMapper carSeriesMapper;

    public ArrayList<CarBrandResp> queryCarBrandList() {
        log.info("CarManager begin to queryCarBrandList");
        List<CarBrandResp> carBrandRespList = carBrandMapper.selectAllList();
        log.info("CarManager queryCarBrandList end...");
        return (ArrayList<CarBrandResp>) carBrandRespList;
    }


    public ArrayList<CarSpecResp> queryCarSpecListByBrandId(CarSpecReq carSpecReq) {
        log.info("CarManager begin to queryCarSpecListByBrandId, carSpecReq = [{}]", carSpecReq);
        List<CarSpecResp> carSpecRespList = carSpecMapper.selectListByCarBrandId(carSpecReq.getCarBrandId());
        log.info("CarManager queryCarSpecListByBrandId end...");
        return (ArrayList<CarSpecResp>) carSpecRespList;
    }

    public ArrayList<CarSeriesResp> queryCarSeriesListByCarBrandIdAndCarSepcId(CarSeriesReq carSeriesReq) throws BaseException {
        log.info("CarManager begin to queryCarSeriesListByCarBrandIdAndCarSepcId, carSeriesReq = [{}]", carSeriesReq);
        if (carSeriesReq == null) {
            throw new BaseException(OfferBaseMessageCodeEnum.REQUEST_IS_NULL);
        }
        if (StringUtils.isEmpty(carSeriesReq.getCarBrandId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAR_BRAND_ID_IS_NULL);
        }
        if (StringUtils.isEmpty(carSeriesReq.getCarSpecId())) {
            throw new BaseException(OfferBaseMessageCodeEnum.CAR_SPEC_ID_IS_NULL);
        }
        List<CarSeriesResp> carSeriesRespList = carSeriesMapper.selectListByCarBrandIdAndCarSepcId(carSeriesReq.getCarBrandId(), carSeriesReq.getCarSpecId());
        log.info("CarManager queryCarSpecListByBrandId end...");
        return (ArrayList<CarSeriesResp>) carSeriesRespList;
    }


}
