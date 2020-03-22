package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
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
public class ModSaleCatReq implements Serializable {

    private static final long serialVersionUID = 650591833855499399L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "LOGO")
    private String logo;

    @ApiModelProperty(value = "排序")
    private Long catOrder;

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

    @ApiModelProperty(value = "是否活动目录")
    private String activeFlag;

    /**
     * 添加关联商品
     */
    @ApiModelProperty(value = "添加关联商品")
    private List<String> offerIdList;

}
