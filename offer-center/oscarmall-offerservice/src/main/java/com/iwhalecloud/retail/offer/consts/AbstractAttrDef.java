package com.iwhalecloud.retail.offer.consts;


/**
 * 属性
 * @author fanxiaofei
 * @date 2019/03/07
 */
public abstract class AbstractAttrDef {

    private AbstractAttrDef() { }

    /**
     * 可空
     */
    public static final String NULLABLE_Y = "Y";

    /**
     * 非空
     */
    public static final String NULLABLE_N = "N";

    /**
     * 输入框类型 文本
     */
    public static final int INPUT_TYPE_TEXT = 1;

    /**
     * 输入框类型 单选
     */
    public static final int INPUT_TYPE_SELECT = 2;

    /**
     * 输入框类型 多选
     */
    public static final int INPUT_TYPE_MULTI_SELECT = 3;

    /**
     * 输入框类型 自定义
     */
    public static final int INPUT_TYPE_CUSTOM = 4;


    /**
     * A:规格属性 B:实例化属性 C:SKU属性
     */
    public static final String ATTR_TYPE_A = "A";

    /**
     * A:规格属性 B:实例化属性 C:SKU属性
     */
    public static final String ATTR_TYPE_B = "B";

    /**
     * A:规格属性 B:实例化属性 C:SKU属性
     */
    public static final String ATTR_TYPE_C = "C";

    /**
     * 是否继承属性
     */
    public static final String ABSTRACT_ATTR_DEF_Y = "Y";

    /**
     * 是否继承属性
     */
    public static final String ABSTRACT_ATTR_DEF_N = "N";

    /**
     * 是否开启SKU
     */
    public static final String TURN_ON_SKU_Y = "Y";

    /**
     * 是否开启SKU
     */
    public static final String TURN_ON_SKU_N = "N";

    /**
     * 1:商品私有属性 2.目录私有属性
     */
    public static final String PRIVATE_TYPE_ONE = "1";

    /**
     * 1:商品私有属性 2.目录私有属性
     */
    public static final String PRIVATE_TYPE_TWO = "2";
}
