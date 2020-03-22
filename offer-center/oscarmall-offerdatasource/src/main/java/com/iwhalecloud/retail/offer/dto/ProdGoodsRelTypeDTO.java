package com.iwhalecloud.retail.offer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品关系类型
 * @author fanxiaofei
 * @date 2019/02/26
 */
@Data
@ApiModel(value = "对应模型prod_goods_rel_type, 对应实体ProdGoodsRelTypeDTO类")
public class ProdGoodsRelTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String relType;

    @ApiModelProperty(value = "名称")
    private String relTypeName;

    @ApiModelProperty(value = "描述")
    private String comments;

}

