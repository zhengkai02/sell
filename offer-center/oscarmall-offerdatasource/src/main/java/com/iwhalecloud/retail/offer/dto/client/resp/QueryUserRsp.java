package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户详情出参
 * 调用亚信接口
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class QueryUserRsp implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "来源编号")
    private String source;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "会员名称")
    private String username;

    @ApiModelProperty(value = "号码归属地")
    private String phoneAttribution;

}
