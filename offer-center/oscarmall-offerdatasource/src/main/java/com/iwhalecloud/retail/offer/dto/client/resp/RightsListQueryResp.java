package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询用户权益出参
 *
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class RightsListQueryResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券列表
     */
    @ApiModelProperty(value = "优惠券列表")
    private List<RightsQueryResp> rightList;

}
