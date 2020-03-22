package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/25 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
public class ProdGoodsCat implements Serializable {

    /**表名常量*/
    public static final String TNAME = "prod_goods_cat";

    private static final long serialVersionUID = -867713730446451167L;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String name;

    /**
     * 目录标识
     */
    @ApiModelProperty(value = "目录标识")
    private String catId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String catType;

    /**
     * 上级目录标识
     */
    @ApiModelProperty(value = "上级目录标识")
    private String parentId;

    /**
     * 店铺
     */
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    /**
     * LOGO
     */
    @ApiModelProperty(value = "LOGO")
    private String logo;

    /**
     * realLOGO
     */
    @ApiModelProperty(value = "真实logo")
    private String realLogo;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long catOrder;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String comments;

    /**
     * TANANT_ID
     */
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "活动标识")
    private String activeFlag;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "Y:可以开发票 N/Null:不可以开发票")
    private String invoiceFlag;

    @ApiModelProperty(value = "发票类目名称")
    private String invoiceCatgName;

    @ApiModelProperty(value = "长安的appCode，只有长安的应用才有appCode")
    private String appCode;

    /**
     * 长安的brandId
     */
    @ApiModelProperty(value = "长安的产品归属品牌 [Integer] 0：乘用车 1：新能源 2：商用车")
    private Integer brandId;

    /**
     * 长安应用的同步状态，A-新增未同步，U-修改未同步，Y-已同步.
     */
    @ApiModelProperty(value = "长安应用的同步状态:A-新增未同步，U-修改未同步，Y-已同步")
    private String syncState;

}
