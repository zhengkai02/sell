package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huminghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/8 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_goods_price")
public class GoodsPrice implements Serializable {

    private static final long serialVersionUID = -1159679791916672307L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "定价标识")
    private String goodsPriceId;

    @ApiModelProperty(value = "商品标识")
    private String goodsId;

    @ApiModelProperty(value = "表达式")
    private String expression;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "生效时间")
    private Date effDate;

    @ApiModelProperty(value = "失效时间")
    private Date expDate;

    @ApiModelProperty(value = "优先级")
    private Long priority;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "tenantId")
    private String tenantId;
}
