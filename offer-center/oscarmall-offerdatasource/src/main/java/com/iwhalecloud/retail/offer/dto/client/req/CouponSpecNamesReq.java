package com.iwhalecloud.retail.offer.dto.client.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/19 <br>
 * @see com.iwhalecloud.retail.coupon.dto.req <br>
 * @since IOT <br>
 */
@Data
public class CouponSpecNamesReq implements Serializable {
    private List<Long> couponSpecIds;
}
