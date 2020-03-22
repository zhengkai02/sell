package com.iwhalecloud.retail.offer.dto.client;

import com.iwhalecloud.retail.offer.dto.client.resp.CouponIdAndSpecNameDTO;
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
 * @see com.iwhalecloud.retail.coupon.dto.resp <br>
 * @since IOT <br>
 */
@Data
public class CouponSpecNamesResp implements Serializable {
    private List<CouponIdAndSpecNameDTO> couponIdAndSpecNameList;
}
