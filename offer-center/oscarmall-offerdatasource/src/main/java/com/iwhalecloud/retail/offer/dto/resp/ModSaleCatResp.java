package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;

import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/31 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0<br>
 */
@Data
public class ModSaleCatResp implements Serializable {

    private static final long serialVersionUID = -7668665948905604734L;

    @ApiModelProperty(value = "目录ID")
    private String catId;

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
     * 关联的管理目录信息
     */
    @ApiModelProperty(value = "关联的管理目录信息")
    private ProdGoodsCat prodGoodsCat;
}
