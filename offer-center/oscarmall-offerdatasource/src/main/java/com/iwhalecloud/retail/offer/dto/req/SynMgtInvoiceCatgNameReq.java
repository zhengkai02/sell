package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.List;

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
public class SynMgtInvoiceCatgNameReq implements Serializable {

    private static final long serialVersionUID = 3614639352247385928L;

    @ApiModelProperty(value = "流水号")
    private String requestId;

    @ApiModelProperty(value = "发票类目详情")
    private List<InvoiceCatgDetail> invoiceCatgDetail;
}
