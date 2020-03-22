package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.iwhalecloud.retail.common.dto.UserDTO;
import com.iwhalecloud.retail.offer.dto.resp.ProdGoodsSalesRuleResp;
import com.iwhalecloud.retail.offer.dto.resp.TagResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/5 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class AddProdGoodsReq implements Serializable {

    private static final long serialVersionUID = -7757559536272177235L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "商品编码")
    private String sn;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "搜索词")
    private String searchKey;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "是否实名认证 0 否  1 是")
    private Integer isCertification;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "副标题")
    private String simpleName;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "店铺目录ID")
    private String storeCatId;

    @ApiModelProperty(value = "优先级")
    private Long sord;

    @ApiModelProperty(value = "销售目录ID")
    private String saleCatId;

    @ApiModelProperty(value = "重复订购标志")
    private String duplicateFlag;

    @ApiModelProperty(value = "允许的最大订购数量")
    private Long maxBuyCount;

    @ApiModelProperty(value = "售价")
    private Long price;

    @ApiModelProperty(value = "市场价")
    private Long mktprice;

    @ApiModelProperty(value = "库存")
    private Long stockQty;

    @ApiModelProperty(value = "是否推荐商品")
    private String isRecommend;

    @ApiModelProperty(value = "标题集合")
    private List<TagResp> tagList;

    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;

    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;

    @ApiModelProperty(value = "是否关怀商品")
    private String careFlag;

    @ApiModelProperty(value = "评价审核方式")
    private String evaluateAuditMode;

    @ApiModelProperty(value = "评论数量调整值")
    private Long adjBuyCount;

    @ApiModelProperty(value = "税费")
    private Long tax;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "商品内容")
    private List<ProdGoodsContentReq> prodGoodsContentList;

    @ApiModelProperty(value = "是否开启SKU")
    private String turnOnSku;

    @ApiModelProperty(value = "商品属性值")
    private List<ProdGoodsAttrValueReq> prodGoodsAttrValueList;

    @ApiModelProperty(value = "商品适用规则")
    private List<ProdGoodsSalesRuleResp> prodGoodsSaleRuleList;

    @ApiModelProperty(value = "商品关系")
    private List<ProdGoodsRelReq> prodGoodsRelList;
}
