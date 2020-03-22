package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName TblStoreCatgResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/4/29 13:40
 **/
@Data
public class TblStoreCatgResp implements Serializable {

    private static final long serialVersionUID = -3805393069571090329L;

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

    @ApiModelProperty(value = "realLogo")
    private String realLogo;

    @ApiModelProperty(value = "排序")
    private Long catOrder;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "children")
    private List<TblStoreCatgResp> children;

}
