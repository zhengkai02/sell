package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.CarSeriesReq;
import com.iwhalecloud.retail.offer.dto.req.CarSpecReq;
import com.iwhalecloud.retail.offer.dto.resp.CarBrandResp;
import com.iwhalecloud.retail.offer.dto.resp.CarSeriesResp;
import com.iwhalecloud.retail.offer.dto.resp.CarSpecResp;
import com.iwhalecloud.retail.offer.manager.CarManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/12 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since CRM <br>
 */
@Api(tags = "车辆")
@Slf4j
@RestController
@RequestMapping("/offer/car")
public class CarController {

    @Autowired
    private CarManager carManager;

    @PostMapping("/brand/list")
    public ResultVO<ArrayList<CarBrandResp>> queryCarBrandList() {
        log.info("AttrController queryCarBrandList start");
        ResultVO<ArrayList<CarBrandResp>> result = ResultVOCheckUtil.buildResultVO(carManager::queryCarBrandList);
        log.info("AttrController queryCarBrandList end");
        return result;
    }

    @PostMapping("/spec/list")
    public ResultVO<ArrayList<CarSpecResp>> queryCarSpecListByBrandId(@RequestBody CarSpecReq carSpecReq) {

        log.info("AttrController queryCarSpecListByBrandId start");
        ResultVO<ArrayList<CarSpecResp>> result = ResultVOCheckUtil.buildResultVO(carManager::queryCarSpecListByBrandId, carSpecReq);
        log.info("AttrController queryCarSpecListByBrandId end");
        return result;
    }

    @PostMapping("/series/list")
    public ResultVO<ArrayList<CarSeriesResp>> queryCarSeriesListByCarBrandIdAndCarSepcId(@RequestBody CarSeriesReq carSeriesReq) {
        log.info("AttrController queryCarSeriesListByCarBrandIdAndCarSepcId start");
        ResultVO<ArrayList<CarSeriesResp>> result = ResultVOCheckUtil.buildResultVO(carManager::queryCarSeriesListByCarBrandIdAndCarSepcId, carSeriesReq);
        log.info("AttrController queryCarSeriesListByCarBrandIdAndCarSepcId end");
        return result;
    }
}

