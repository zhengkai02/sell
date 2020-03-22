package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SPU关联商品
 * 
 * @author fanxiaofei
 * @date 2019-05-27
 */
@Data
public class AddSpuGoodsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "租户")
    private String tenantId;

    @ApiModelProperty(value = "关联商品")
    private List<AddSpuRelGoodsReq> addSpuRelGoodsReqs;

}
