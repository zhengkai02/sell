package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 自动库存上下架
 * 
 * @author fanxiaofei
 * @date 2019-03-04
 */
@Data
public class ProdGoodsStockWaveReq implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String comments;

    /**
     * 创建操作人
     */
    @ApiModelProperty(value = "创建操作人")
    private String createBy;

    /**
     * 修改操作人
     */
    @ApiModelProperty(value = "修改操作人")
    private String modifyBy;

    /**
     * 新增操作时间
     */
    @ApiModelProperty(value = "新增操作时间")
    private Date createTime;

    /**
     * 修改操作时间
     */
    @ApiModelProperty(value = "修改操作时间")
    private Date modifyTime;

    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;

}
