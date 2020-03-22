package com.iwhalecloud.retail.offer.consts;

/**
 * MQ
 *
 * @author fanxiaofei
 * @date 2019/07/03
 */
public abstract class AbstractOscarMallAuditResultDef {

    private AbstractOscarMallAuditResultDef() {
    }

    /**
     * 是否审批 0-否 1-是
     */
    public static final Integer IS_APPROVAL_ZERO = 0;

    /**
     * 审批内容类型：15:商品 16:文章 17:优惠券
     */
    public static final Integer BUSINESS_TYPE_A = 15;

    /**
     * 审批内容类型：15:商品 16:文章 17:优惠券
     */
    public static final Integer BUSINESS_TYPE_B = 16;

    /**
     * 审批内容类型：15:商品 16:文章 17:优惠券
     */
    public static final Integer BUSINESS_TYPE_C = 17;

    /**
     * 记录类型：1新增，2修改，3删除，4投放，5启用，6停用，7出库，8入库
     */
    public static final String APPROVAL_TYPE_TWO = "2";

    /**
     * 1：审批成功 0: 审批失败
     */
    public static final String RESULT_ZERO = "0";

}
