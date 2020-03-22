package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName OfferCatgReq
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/20 13:46
 **/
@Data
public class OfferCatgReq implements Serializable {

    private static final long serialVersionUID = 4263314188526381388L;

    @ApiModelProperty(value = "流水号")
    private Long requestId;

}
