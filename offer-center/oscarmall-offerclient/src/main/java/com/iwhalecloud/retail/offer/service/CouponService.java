package com.iwhalecloud.retail.offer.service;


import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.offer.dto.client.CouponSpecNamesResp;
import com.iwhalecloud.retail.offer.dto.client.req.CouponSpecNamesReq;
import com.iwhalecloud.retail.offer.dto.req.CouponSpecRuleRelaReq;
import com.iwhalecloud.retail.offer.dto.resp.CouponSpecRuleRelaResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

/**
 * 优惠券
 * @author fanxiaofei
 * @date 2019-03-12
 */
@FeignClient(name = "${oscar.rest.coupon.name}", path = "${oscar.rest.coupon.path}")
public interface CouponService {

    /**
     * 调用coupon-center
     * @param couponId 优惠券id
     * @return CouponFeeResp
     */
    @PostMapping("/fee")
    void calcCouponFee(String couponId);

    /**
     * 根据优惠券id查询优惠券规格名称
     * @param couponSpecNamesReq CouponSpecNamesReq
     * @return ResultVO<CouponSpecNamesResp>
     */
    @PostMapping("/qryCouponSpecNamesByCouponSpecIds")
    ResultVO<CouponSpecNamesResp> qryCouponSpecNamesByCouponSpecIds(CouponSpecNamesReq couponSpecNamesReq);

    /**
     * @Author wangzhongbao
     * @Description 根据优惠券和优惠券规格，查询适用商品条件
     * @Date 16:51 2019/3/22
     * @Param [couponReq]
     * @return com.iwhalecloud.retail.common.dto.ResultVO<java.util.ArrayList<com.iwhalecloud.retail.offer.dto.resp.CouponSpecRuleRelaResp>>
     **/
    @PostMapping("/coupon/spec/rule/relal/ist")
    ResultVO<ArrayList<CouponSpecRuleRelaResp>> qryCouponRelaList(@RequestBody CouponSpecRuleRelaReq couponReq);
}