package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/24 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class InvoiceCatgDetail implements Serializable {

    private static final long serialVersionUID = -1373378251937492392L;

    @ApiModelProperty(value = "商品标识号")
    private String goodsItemSerial;

    @ApiModelProperty(value = "发票类目名称")
    private String invoiceCatgName;

}
