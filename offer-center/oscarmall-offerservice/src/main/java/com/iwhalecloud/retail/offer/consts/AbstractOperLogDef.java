package com.iwhalecloud.retail.offer.consts;


/**
 * 操作日志
 * @author fanxiaofei
 * @date 2019/06/01
 */
public abstract class AbstractOperLogDef {

    private AbstractOperLogDef() { }

    /**
     * 批量删除商品
     */
    public static final Long OPER_EVENT_ONE = 1L;

    /**
     * 批量删除优惠券
     */
    public static final Long OPER_EVENT_TWO = 2L;

    /**
     * 批量删除活动
     */
    public static final Long OPER_EVENT_THREE = 3L;

    /**
     * 批量删除订单
     */
    public static final Long OPER_EVENT_FOUR = 4L;

    /**
     * 批量上架商品
     */
    public static final Long OPER_EVENT_FIVE = 5L;

    /**
     * 批量下架商品
     */
    public static final Long OPER_EVENT_SIX = 6L;

    /**
     * 批量审核评论
     */
    public static final Long OPER_EVENT_SEVEN = 7L;


    /**
     * 创建
     */
    public static final String OPER_TYPE_C = "C";

    /**
     * 读取
     */
    public static final String OPER_TYPE_R = "R";

    /**
     * 更新
     */
    public static final String OPER_TYPE_U = "U";

    /**
     * 删除
     */
    public static final String OPER_TYPE_D = "D";
}
