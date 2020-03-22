package com.iwhalecloud.retail.offer.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName CouponSpecRuleRelaResp
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/22 16:45
 **/
@Data
public class CouponSpecRuleRelaResp implements Serializable {

    private static final long serialVersionUID = 6910342823341249420L;

    private Long couponSpecId;

    private Long couponSpecRuleId;

    private String objId;

    private String tenantId;
}
