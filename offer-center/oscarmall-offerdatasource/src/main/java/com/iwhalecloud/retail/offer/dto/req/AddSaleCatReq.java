package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/31 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class AddSaleCatReq implements Serializable {

    private static final long serialVersionUID = 9050333449849264937L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "目录类型")
    private String catType;

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

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识")
    private String tenantId;

    /**
     * 创建用户标识
     */
    @ApiModelProperty(value = "创建用户标识")
    private String createBy;

    /**
     * 关联的管理目录标识
     */
    @ApiModelProperty(value = "关联的管理目录标识")
    private String relaCatId;

    @ApiModelProperty(value = "是否活动标识")
    private String activeFlag;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    /**
     * 添加关联商品
     */
    @ApiModelProperty(value = "添加关联商品")
    private List<String> offerIdList;
}
