package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @version 1.0
 * @ClassName TblStoreCatgMemQryReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/4/29 15:45
 **/
@Data
public class TblStoreCatgMemQryReq extends PageVO {

    private static final long serialVersionUID = -6195997705300477925L;

    @ApiModelProperty(value = "目录类目ID")
    private String catMemId;

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

}
