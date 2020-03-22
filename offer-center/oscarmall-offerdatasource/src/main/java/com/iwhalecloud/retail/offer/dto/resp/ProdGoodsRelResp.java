package com.iwhalecloud.retail.offer.dto.resp;

import com.iwhalecloud.retail.offer.entity.ProdGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/5 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProdGoodsRelResp extends ProdGoods implements Serializable {

    private static final long serialVersionUID = -4058802365743882479L;

    @ApiModelProperty(value = "商品关系ID")
    private String relId;

    @ApiModelProperty(value = "A端商品id")
    private String aGoodsId;

    @ApiModelProperty(value = "商品关系类型")
    private String relType;

    @ApiModelProperty(value = "商品关系类型名称")
    private String relTypeName;

    @ApiModelProperty(value = "Z端商品id")
    private String zGoodsId;

}
