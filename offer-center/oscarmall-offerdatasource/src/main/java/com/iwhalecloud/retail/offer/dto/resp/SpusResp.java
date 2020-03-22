package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * SPU
 * 
 * @author fanxiaofei
 * @date 2019-06-10
 */
@Data
public class SpusResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu标识")
    private String spuId;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "属性")
    private List<SpuAttrsResp> attrs;

    @ApiModelProperty(value = "spu标识")
    private List<SkusResp> skus;
}
