package com.iwhalecloud.retail.common.consts;

import org.apache.commons.lang3.StringUtils;

import com.iwhalecloud.retail.common.config.ErrorCodeConfig;

public enum CommonBaseMessageCodeEnum implements BaseMessageIntf {

    SUCCESS("0000", "成功"),
    PARAM_ERROR("9000", "参数异常"),
    RESPONSE_ERROR("9001", "返回数据异常"),
    REQ_PARAM_NOT_NULL("9002", "请求参数不能为空"),
    GENERATE_TOKEN_FAIL("9003", "Token生成失败"),
    USER_ID_IS_NULL("9004", "用户标识符不能为空"),
    FILE_IS_NULL("9005", "文件不能为空"),
    RES_ID_IS_NULL("9006", "资源标识不能为空"),
    CHANNEL_ID_IS_NULL("9007", "渠道标识不能为空"),
    ORDER_ID_IS_NULL("9008", "订单标识不能为空"),
    LOGIN_INVALID("9009", "未登录或登录超时，请重新登录"),
    NOTIFY_IS_ALREADY_READ("9010", "站内信已读"),
    PIC_TYPE_ERROE("9011", "当前仅支持以下图片类型：jpg,gif,png,ico,bmp,jpeg"),
    TABLE_NAME_NOT_EXIST("9012", "记录日志失败，未查到表名"),
    TABLE_PK_NOT_EXIST("9013", "记录日志失败，未查到唯一标识"),
    NOT_FOUND_MQ_RULE("9014", "未查询到消息队列规则"),
    DATE_FORMART_ERROR("9015", "日期格式转换错误"),
    TENANT_ID_IS_NULL("9016", "租户标识符不能为空"),
    CHANGES_ID_IS_NULL("9020", "修改内容不能为空"),
    APPROVAL_TYPE_IS_NULL("9021", "记录类型不能为空"),
    APPROVAL_URL_IS_NULL("9022", "审批跳转页面不能为空"),
    USER_NAME_IS_NULL("9023", "用户名称不能为空"),
    BUSINESS_TYPE_IS_NULL("9024", "审批内容类型为空"),
    BUSINESS_ID_IS_NULL("9025", "审批内容类型结果为空"),
    CHANNEL_ID_IS_ERROR("9026", "渠道标识错误"),
    RECEIVER_MQ_ERROR("9027", "接收MQ错误"),
    SYSTEM_ERROR("9999", "系统异常,请稍后再试"),
    // 长安系统错误码定义
    CHANGAN_SYSTEM_ERROR("9100", "长安系统异常,请稍后再试"),
    CHANGAN_SYNC_FAIL("9101", "状态已经是同步状态");

    private String status;

    private String message;

    CommonBaseMessageCodeEnum(String status, String message) {
        if (StringUtils.isNotEmpty(ErrorCodeConfig.errorCodePrefix)) {
            this.status = ErrorCodeConfig.errorCodePrefix + status;
        }
        else {
            this.status = status;
        }
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
