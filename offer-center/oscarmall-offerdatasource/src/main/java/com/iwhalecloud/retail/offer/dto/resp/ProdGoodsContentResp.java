package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName ProdGoodsContentResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/3/21 15:45
 **/
@Data
public class ProdGoodsContentResp implements Serializable {

    /**
     * contentId
     **/
    @ApiModelProperty(value = "内容ID")
    private String contentId;

    /**
     * 排序，小的在前面显示
     **/
    @ApiModelProperty(value = "排序，小的在前面显示")
    private Long sort;

    /**
     * P:图片 R：富文本
     **/
    @ApiModelProperty(value = "内容类型")
    private String contentType;

    /**
     * 图片路径 或者html
     **/
    @ApiModelProperty(value = "图片路径 或者html")
    private String content;

    /**
     * 段落标题
     **/
    @ApiModelProperty(value = "段落标题")
    private String title;


}
