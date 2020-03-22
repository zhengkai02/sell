package com.iwhalecloud.retail.changan.dto.resp;

import com.iwhalecloud.retail.changan.dto.ProdGoodsCat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/2/28 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsCatListResp {

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
    @ApiModelProperty(value = "LOGO")
    private String logo;

    /**
     * real LOGO
     */
    @ApiModelProperty(value = "realLogo")
    private String realLogo;

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

    @ApiModelProperty(value = "是否是活动标识")
    private String activeFlag;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    /**
     * 如果是销售目录，查询关联的管理目录
     */
    @ApiModelProperty(value = "商品目录")
    private ProdGoodsCat prodGoodsCat;

    @ApiModelProperty(value = "商品目录名称")
    private String prodGoodsCatName;

    @ApiModelProperty(value = "商品品牌名称")
    private String goodsBrandNames;

    @ApiModelProperty(value = "商品品牌")
    private List<TblGoodsBrandTenantResp> goodsBrands;

    /**
     * children
     */
    @ApiModelProperty(value = "children")
    private List<QryGoodsCatListResp> children;

    @ApiModelProperty(value = "sku属性")
    private List<GoodsCatAttrResp> skuGoodsCatAttr;

    @ApiModelProperty(value = "非sku属性")
    private List<GoodsCatAttrResp> noSkuGoodsCatAttr;

    /**
     * 长安的appCode，对应到销售目录的目录编码catCode.
     */
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof QryGoodsCatListResp)) {
            return  false;
        }
        else {
            QryGoodsCatListResp objForm = (QryGoodsCatListResp) obj;
            return this.catId.equals(objForm.catId);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
