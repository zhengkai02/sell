package com.iwhalecloud.retail.changan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghw
 * @data 2019-11-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferToJDRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业 ID
     */
    private String enterpriseId;

    /**
     * 商品 id
     */
    private String productSerial;

    /**
     * 商品名.
     */
    private String productName;

    /**
     * 商品描述.
     */
    private String productDescription;

    /**
     * 商品售价.
     */
    private Integer price;

    /**
     * 商品状态.
     */
    private Integer status;

    /**
     * 生效日期.
     */
    private String activeTime;

    /**
     * 过期日期.
     */
    private String expireTime;

    /**
     * 商品类型.
     * 1: C端商品 2: B端商品 3:个人初始 赠送商品")
     */
    private Integer purchaseMode;

    /**
     * 商品适用卡类型.
     * 1：正常个人用车 2：展车 3：特殊场景用车
     */
    private Integer[] eSIMTypes;

    /**
     * 周期内阈值.N
     */
    private Long threshold;

    /**
     * 购买周期.N
     * 1：不限 2：年 3：季 4：月  5：半年
     */
    private Integer period;

    /**
     * 商品属性列表.
     */
    private List<Attributes> attributes;

    /**
     * 商品对 CMP 应套餐列表.
     */
    private List packageList;

    /**
     * 同步类型.
     * 0:新增,1:修改,2:删除
     */
    private Integer syncType;

    /**
     * 开票内容.
     */
    private String invoiceContent;


}
