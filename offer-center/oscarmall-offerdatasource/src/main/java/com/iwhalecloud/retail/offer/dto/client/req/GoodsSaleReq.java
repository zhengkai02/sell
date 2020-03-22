package com.iwhalecloud.retail.offer.dto.client.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/15 <br>
 * @see com.iwhalecloud.retail.order.dto.client <br>
 * @since V9.0C<br>
 */
@Data
public class GoodsSaleReq implements Serializable {

    private static final long serialVersionUID = 882379562667198957L;

    /**
     * 唯一请求号，保证每次请求唯一。
     */
    private Long requestId;

    /**
     * 购买人手机号
     */
    private String phone;

    /**
     * 订单编码
     */
    private String mallOrderCode;

    /**
     * 销售时间
     */
    private String saleTime;

    /**
     * 支付状态 0:未支付1:支付成功 2：支付失败
     */
    private Integer payStatus;

    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal payAmount;

    /**
     * 订单关联商品列表
     */
    private List<GoodsSaleGoodsReq> goodsList;

    /**
     * 优惠券列表
     */
    private List<GoodsSaleCoupondReq> coupondList;

}
