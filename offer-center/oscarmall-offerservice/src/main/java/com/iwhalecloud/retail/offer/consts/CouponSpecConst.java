package com.iwhalecloud.retail.offer.consts;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/26 <br>
 * @see com.iwhalecloud.retail.offer.consts <br>
 * @since CRM <br>
 */
public abstract class CouponSpecConst {

    private CouponSpecConst() { }

    public static final String STATE_CREATE = "A";

    public static final String STATE_TO_PUBLISH = "B";

    public static final String STATE_PUBLISHED = "C";

    public static final String STATE_EXP = "X";

    public static final String EFF_TYPE_TIME_PERIOD = "1";

    public static final String EFF_TYPE_IMMEDIATELY = "2";

    public static final String EFF_TYPE_NEXT_DAY = "3";

    public static final String YES = "Y";

    public static final String NO = "N";

}
