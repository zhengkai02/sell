package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情封装的content
 * @author fanxiaofei
 * @date 2019/02/28
 */
@Data
public class ContentResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序,小的在前面显示")
    private Long sort;

    @ApiModelProperty(value = "P: 图片 R：富文本")
    private String contentType;

    @ApiModelProperty(value = "图片路径 或者html")
    private String content;

}

