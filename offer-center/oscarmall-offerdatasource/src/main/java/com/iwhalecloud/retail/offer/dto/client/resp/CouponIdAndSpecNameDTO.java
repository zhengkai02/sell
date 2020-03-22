package com.iwhalecloud.retail.offer.dto.client.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/19 <br>
 * @see com.iwhalecloud.retail.coupon.dto <br>
 * @since IOT <br>
 */
@Data
public class CouponIdAndSpecNameDTO implements Serializable {
    private Long couponSpecId;

    private String couponSpecName;

    private Long discountRate;

    private Long discountAmount;

    private String comments;

    private String couponType;
}
