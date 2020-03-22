package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SPU
 * 
 * @author fanxiaofei
 * @date 2019-05-26
 */
@Data
public class UpdateSpuReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

}
