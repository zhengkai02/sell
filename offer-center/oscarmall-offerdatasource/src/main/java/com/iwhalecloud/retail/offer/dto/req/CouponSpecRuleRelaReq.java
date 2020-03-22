package com.iwhalecloud.retail.offer.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/22 <br>
 * @see com.iwhalecloud.retail.coupon.dto <br>
 * @since CRM <br>
 */
@Data
public class CouponSpecRuleRelaReq implements Serializable {

    private Long couponId;

    private Long couponSpecId;

    private List<Long> couponSpecRuleIdList;
}
