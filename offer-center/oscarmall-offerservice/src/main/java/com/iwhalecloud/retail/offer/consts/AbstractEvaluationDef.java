package com.iwhalecloud.retail.offer.consts;


/**
 * 评价状态
 * @author fanxiaofei
 * @date 2019/05/07
 */
public abstract class AbstractEvaluationDef {

    private AbstractEvaluationDef() { }

    /**
     * A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    public static final String STATE_A = "A";

    /**
     * A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    public static final String STATE_B = "B";

    /**
     * A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    public static final String STATE_C = "C";

    /**
     * A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    public static final String STATE_D = "D";

    /**
     * A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    public static final String STATE_X = "X";

    /**
     * A 商品, B 内容, C 订单
     */
    public static final String OBJ_TYPE_A = "A";

    /**
     * A 商品, B 内容, C 订单
     */
    public static final String OBJ_TYPE_B = "B";

    /**
     * A 商品, B 内容, C 订单
     */
    public static final String OBJ_TYPE_C = "C";

    /**
     * 自动审核 Y表示需要,为N表示不需要
     */
    public static final String EVALUATION_AUDIT_FLAG_Y = "Y";

    /**
     * 自动审核 Y表示需要,为N表示不需要
     */
    public static final String EVALUATION_AUDIT_FLAG_N = "N";

    /**
     * A 自动 M 手动
     */
    public static final String EVALUATION_AUDIT_FLAG_A = "A";

    /**
     * A 自动 M 手动
     */
    public static final String EVALUATION_AUDIT_FLAG_M = "M";

    /**
     * 表名称
     */
    public static final String EVALUATION = "evaluation";

    /**
     * 字段名称
     */
    public static final String STATE = "state";

    /**
     * 是否评价
     */
    public static final String EVALUATION_Y = "Y";

    /**
     * 是否评价
     */
    public static final String EVALUATION_N = "N";

    /**
     * 5  好评  3 中评  1 差评
     */
    public static final String RATE_FIVE = "5";

    /**
     * 5  好评  3 中评  1 差评
     */
    public static final String RATE_THREE = "3";

    /**
     * 5  好评  3 中评  1 差评
     */
    public static final String RATE_ONE = "1";
}
