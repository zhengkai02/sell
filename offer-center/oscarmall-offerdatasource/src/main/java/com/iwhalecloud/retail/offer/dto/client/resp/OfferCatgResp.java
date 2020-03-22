package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName OfferCatgResp
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/20 13:41
 **/
@Data
public class OfferCatgResp implements Serializable {

    private static final long serialVersionUID = 8367411466385218200L;

    /**
     *目录标识
     **/
    @ApiModelProperty(value = "目录标识")
    private String offerCatgId;

    /**
     *目录名称
     **/
    @ApiModelProperty(value = "目录名称")
    private String offerCatgName;

    /**
     *父标识
     **/
    @ApiModelProperty(value = "父标识")
    private String parentId;


}
