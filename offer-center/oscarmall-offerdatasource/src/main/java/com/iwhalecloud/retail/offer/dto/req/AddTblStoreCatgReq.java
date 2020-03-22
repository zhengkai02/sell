package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName AddTblStoreCatgReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/4/28 16:08
 **/
@Data
public class AddTblStoreCatgReq implements Serializable {

    private static final long serialVersionUID = -4427640997682106027L;

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "父级ID")
    private String parentId;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "排序")
    private Long catOrder;




}
