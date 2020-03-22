package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户详情入参
 * 调用亚信接口 M:必传 0:选传
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class QueryUserReq implements Serializable {

    @ApiModelProperty(value = "唯一请求号 M")
    private Long requestId;

    @ApiModelProperty(value = "用户id M")
    private String userId;
}
