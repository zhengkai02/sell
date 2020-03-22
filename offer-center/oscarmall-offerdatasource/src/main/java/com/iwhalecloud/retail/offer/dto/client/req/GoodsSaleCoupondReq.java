package com.iwhalecloud.retail.offer.dto.client.req;

import java.io.Serializable;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/15 <br>
 * @see com.iwhalecloud.retail.order.dto.client.req <br>
 * @since V9.0C<br>
 */
@Data
public class GoodsSaleCoupondReq implements Serializable {

    private static final long serialVersionUID = 5072991538419939953L;

    /**
     * 优惠券规格标识
     */
    private String couponSpecId;

    /**
     * 优惠券名称
     */
    private String coupondName;

    /**
     * 数量
     */
    private Integer couponNum;
}
