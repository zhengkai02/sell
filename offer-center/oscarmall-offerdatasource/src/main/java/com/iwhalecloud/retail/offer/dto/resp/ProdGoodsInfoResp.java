package com.iwhalecloud.retail.offer.dto.resp;

import com.iwhalecloud.retail.offer.entity.ProdGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/18 <br>
 * @see com.iwhalecloud.retail.offer.dto.resp <br>
 * @since V9.0C<br>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProdGoodsInfoResp extends ProdGoods {

    private static final long serialVersionUID = -386456618860226950L;

    @ApiModelProperty(value = "销售目录ID")
    private String saleCatId;

    @ApiModelProperty(value = "管理目录ID")
    private String mgnCatId;

    @ApiModelProperty(value = "发票标识")
    private String invoiceFlag;

    /**
     * 店铺机构的id
     */
    @ApiModelProperty(value = "店铺机构的id")
    private String orgId;

    @Override
    public String toString() {
        return "ProdGoodsInfoResp{" +
                "saleCatId='" + saleCatId + '\'' +
                ", mgnCatId='" + mgnCatId + '\'' +
                ", invoiceFlag='" + invoiceFlag + '\'' +
                ", orgId='" + orgId + '\'' +
                "} " + super.toString();
    }
}
