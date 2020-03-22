package com.iwhalecloud.retail.offer.dto.resp;

import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
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
 * @CreateDate 2019/3/6 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProdGoodsCatMemResp extends ProdGoodsCat implements Serializable {

    private static final long serialVersionUID = 232632789446205924L;

    @ApiModelProperty(value = "目录类目ID")
    private String catMemId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;
}