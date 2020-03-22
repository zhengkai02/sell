package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName ProdGoodsStockQtyResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/3/21 10:25
 **/
@Data
public class ProdGoodsStockQtyResp implements Serializable {

    private static final long serialVersionUID = -1695409211390563456L;

    @ApiModelProperty(value = "数量")
    private Long qty;
}
