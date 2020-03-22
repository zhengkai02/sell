package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 店铺详情
 * 
 * @author fanxiaofei
 * @date 2019-04-29
 */
@Data
public class QueryStoreDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺标识")
    private String storeId;

    @ApiModelProperty(value = "机构ID")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺级别")
    private String storeLevelId;

    @ApiModelProperty(value = "店铺级别名称")
    private String storeLevelName;

    @ApiModelProperty(value = "店铺头像")
    private String storeImg;

    @ApiModelProperty(value = "服务地区")
    private String serviceArea;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "银行卡账号")
    private String bankNumber;

    @ApiModelProperty(value = "银行开户名")
    private String bankName;

    @ApiModelProperty(value = "是否为自营店 Y:自营店 N:非自营店")
    private String selfSupportFlag;

    @ApiModelProperty(value = "是否有实体店 Y:有实体店 N:无实体店")
    private String physicalStoreFlag;

    @ApiModelProperty(value = "营业范围")
    private String businessRemarks;

    @ApiModelProperty(value = "营业时间")
    private String businessHours;

    @ApiModelProperty(value = "认证附件")
    private String certAttachment;

    @ApiModelProperty(value = "认证附件")
    private String realAttachment;

    @ApiModelProperty(value = "认证附件")
    private String attachmentName;

    @ApiModelProperty(value = "店铺客服电话")
    private String custServicePhone;

    @ApiModelProperty(value = "状态 A 新建 B 待审批 C 正常 F 审批成功 E:封禁")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "认证不通过原因")
    private String authReason;

    @ApiModelProperty(value = "关店原因")
    private String closeReason;

    @ApiModelProperty(value = "销量")
    private Long salesVolume;

    @ApiModelProperty(value = "店铺等级")
    private Integer storeGrade;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户ID")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "更新用户ID")
    private String modifyBy;

    @ApiModelProperty(value = "封装头像")
    private String realUrl;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "其他联系方式")
    private String otherContactInfo;

    @ApiModelProperty(value = "行业")
    private String industry;

    @ApiModelProperty(value = "店铺地址")
    private String address;

    @ApiModelProperty(value = "信誉分")
    private Integer creditScore;

    @ApiModelProperty(value = "适用规则名称")
    private List<QueryStoreRuleResp> storeRules;

}
