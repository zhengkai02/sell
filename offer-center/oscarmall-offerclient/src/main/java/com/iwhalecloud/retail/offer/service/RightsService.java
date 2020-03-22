package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.resp.RightsListQueryResp;
import com.iwhalecloud.retail.offer.dto.req.RightsReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * 权益
 * 调用亚信
 * @author fanxiaofei
 * @date 2019-03-13
 */
@FeignClient(name = "${oscar.rest.user.name}", path = "${oscar.rest.user.path}")
public interface RightsService {

    /**
     * 查询用户权益
     * @param  rightsReq RightsReq
     * @return ResultVO<RightsQueryResp>
     */
    @PostMapping("/query_user_rights")
    ResultVO<RightsListQueryResp> queryUserRights(RightsReq rightsReq);
}
