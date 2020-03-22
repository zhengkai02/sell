package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SPU
 * 
 * @author fanxiaofei
 * @date 2019-05-24
 */
@Data
public class AddSpuReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

}
