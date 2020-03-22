package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/27 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class AddGoodsResp {
    /**
     * 目录标识
     */
    @ApiModelProperty(value = "目录标识")
    private String catId;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String catType;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String comments;

    /**
     * 店铺
     */
    @ApiModelProperty(value = "店铺")
    private String storeId;

    /**
     * 上级目录标识
     */
    @ApiModelProperty(value = "上级目录标识")
    private String parentId;

    /**
     * LOGO
     */
    @ApiModelProperty(value = "logo")
    private String logo;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long catOrder;

    /**
     * TENANT_ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "是否活动标识")
    private String activeFlag;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;
}
