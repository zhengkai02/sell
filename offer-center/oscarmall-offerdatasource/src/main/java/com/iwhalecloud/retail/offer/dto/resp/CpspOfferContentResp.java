package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class CpspOfferContentResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品内容类型 O")
    private String goodsContentType;

    @ApiModelProperty(value = "设备类型 O")
    private String deviceType;

    @ApiModelProperty(value = "封装具体内容 O")
    private List<Map<String,String>> valueList;
}
