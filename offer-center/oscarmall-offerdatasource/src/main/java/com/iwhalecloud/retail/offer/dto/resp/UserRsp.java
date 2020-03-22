package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.mobile.client.dto.resp <br>
 * @since CRM <br>
 */
@Data
public class UserRsp implements Serializable {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 来源编号
     */
    @ApiModelProperty(value = "来源编号")
    private String source;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     *年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private String birthday;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String userName;

    /**
     *
     */
    @ApiModelProperty(value = "手机属性")
    private String phoneAttribution;

    @ApiModelProperty(value = "渠道")
    private String channel;

}
