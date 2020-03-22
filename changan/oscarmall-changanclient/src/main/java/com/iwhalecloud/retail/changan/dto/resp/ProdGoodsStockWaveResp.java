package com.iwhalecloud.retail.changan.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动库存上下架
 * @author fanxiaofei
 * @date 2019-03-04
 */
@Data
public class ProdGoodsStockWaveResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "上架id")
    private String stockWaveId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;

    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间")
    private Date stockInDate;

    /**
     * 上架数量
     */
    @ApiModelProperty(value = "上架数量")
    private String qty;

    /**
     * 上架方式 C:覆盖 P:追加
     */
    @ApiModelProperty(value = "上架方式 C:覆盖 P:追加")
    private String stockInType;

    /**
     * 状态 A:新建 X:删除 B:上架中 C:上架完成
     */
    @ApiModelProperty(value = "状态 A:新建 X:删除 B:上架中 C:上架完成")
    private String state;

    /**
     * 状态时间
     */
    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String comments;

}
