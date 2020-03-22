package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.req.QueryUserReq;
import com.iwhalecloud.retail.offer.dto.client.resp.QueryUserRsp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 用户详情
 * @author fanxiaofei
 * @date 2019-03-14
 */
@FeignClient(name = "${oscar.rest.user.name}", path = "${oscar.rest.user.path}")
public interface UserService {

    /**
     * 根据用户id查询用户信息
     * @param queryUserReq QueryUserReq
     * @return ResultVO<QueryUserRsp>
     */
    @PostMapping("/query_user")
    ResultVO<QueryUserRsp> queryUserDetail(@RequestBody QueryUserReq queryUserReq);

}
