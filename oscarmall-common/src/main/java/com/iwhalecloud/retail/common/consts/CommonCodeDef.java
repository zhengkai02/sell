package com.iwhalecloud.retail.common.consts;


/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.common.consts <br>
 * @since CRM <br>
 */
public abstract class CommonCodeDef {
    private CommonCodeDef() { }


    //微信服务成功errcode
    public static final String  WECHAT_RESULT_ERRCODE = "errcode";

    //微信服务成功errmsg
    public static final String  WECHAT_RESULT_ERRMSG = "errmsg";

    //微信服务成功errcode
    public static final String  WECHAT_RESULT_CODE_SUCCESS = "0";

    //微信服务成功errmsg
    public static final String  WECHAT_RESULT_CODE_SUCCESS_MESSAGE = "ok";

    //电商接口服务成功resultCode
    public static final String  CMPP_RESULT_CODE_SUCCESS = "0000";

    //支付服务成功返回码
    public static final String TRADE_SUCCESS = "0000_0";

    public static final String ERROR = "4000";

    public static final String STATE_VALID = "A";

    public static final String STATE_INVALID = "X";

    public static final String USER = "USER";

    public static final String VERIFY_CODE = "verifyCode";

    public static final String USER_CUST = "cust";

}
