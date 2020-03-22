package indi.zk.mall.order.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author ZhengKai
 * @data 2019-09-15 14:36
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotBlank(message = "姓名必填")
    private String name;

    /**
     * 买家电话
     */
    @NotBlank(message = "电话必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotBlank(message = "地址必填")
    private String address;

    /**
     * 买家微信
     */
    @NotBlank(message = "openid必填")
    private String openid;

    /**
     * 购物车
     */
    @NotBlank(message = "购物车不能为空")
    private String items;
}
