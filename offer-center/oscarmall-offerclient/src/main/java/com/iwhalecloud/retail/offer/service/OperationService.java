package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.req.AddApprovalsReq;
import com.iwhalecloud.retail.offer.dto.client.resp.AddApprovalsResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 运营中心
 * @author fanxiaofei
 * @date 2019-07-10
 */
@FeignClient(name = "${oscar.rest.operation.name}", path = "${oscar.rest.operation.path}")
public interface OperationService {

    /**
     * 提交审批
     * @param req AddApprovalsReq
     * @return AddApprovalsResp
     */
    @PostMapping(value = "/auth/approvals/add")
    ResultVO<AddApprovalsResp> insertApprovals(@RequestBody AddApprovalsReq req);
}