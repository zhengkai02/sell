package com.iwhalecloud.retail.offer.dto.client.req;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class GoodsSaleGoodsReq implements Serializable {

    private static final long serialVersionUID = 2801390951377978726L;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品售价
     */
    private BigDecimal goodsPrice;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 商品数量
     */
    private Integer prodcutNum;

}
