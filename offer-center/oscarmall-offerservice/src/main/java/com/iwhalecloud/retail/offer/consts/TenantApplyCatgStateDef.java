package com.iwhalecloud.retail.offer.consts;

/**
 * 租户可使用的商品管理目录状态
 * 
 * @author fanxiaofei
 * @date 2019-04-28
 */
public abstract class TenantApplyCatgStateDef {

    private TenantApplyCatgStateDef() {
    }

    /**
     * 有效
     */
    public static final String STATE_A = "A";

    /**
     * 无效
     */
    public static final String STATE_X = "X";
}
