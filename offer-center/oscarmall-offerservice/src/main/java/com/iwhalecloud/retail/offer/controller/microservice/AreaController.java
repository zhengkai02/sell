package com.iwhalecloud.retail.offer.controller.microservice;

import java.util.ArrayList;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.AreaDTO;
import com.iwhalecloud.retail.offer.dto.req.AreaReq;
import com.iwhalecloud.retail.offer.manager.AreaManager;

import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/24 <br>
 * @see com.iwhalecloud.retail.offer.controller.microservice <br>
 * @since CRM <br>
 */
@Api(tags = "地区")
@RestController
@RequestMapping("/offer/area")
@Slf4j
public class AreaController {

    @Autowired
    private AreaManager areaManager;

    @PostMapping("/list/by/level")
    public ResultVO<ArrayList<AreaDTO>> qryAreaListByLevel(@RequestBody AreaReq areaReq) {
        return ResultVOCheckUtil.buildResultVO(areaManager::qryAreaListByLevel, areaReq);
    }

    @PostMapping("/list/by/parentCode")
    public ResultVO<ArrayList<AreaDTO>> qryAreaListByParent(@RequestBody AreaReq areaReq) {
        return ResultVOCheckUtil.buildResultVO(areaManager::qryAreaListByParent, areaReq);
    }
}
