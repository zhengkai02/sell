package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName TblStoreCatgQryReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/4/28 16:50
 **/
@Data
public class TblStoreCatgQryReq implements Serializable {

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

}
