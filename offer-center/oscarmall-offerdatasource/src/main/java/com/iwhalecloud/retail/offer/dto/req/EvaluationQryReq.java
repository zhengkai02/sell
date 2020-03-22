package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName EvaluationQryReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/7 14:50
 **/
@Data
public class  EvaluationQryReq extends AbstractPageReq {

    private static final long serialVersionUID = 937452454530172643L;

    /**
     * A 商品、B 内容，默认A
     */
    @ApiModelProperty(value = "对象类型  A 商品、B 内容，默认A")
    private String objType;

    /**
     * 商品或者文章ID
     */
    @ApiModelProperty(value = "对象ID 商品或者文章ID ")
    private String objId;

    /**
     * 5 好评 3 中评 1 差评， 不传查询所有
     */
    @ApiModelProperty(value = "评分")
    private String rate;

    /**
     *
     */
    @ApiModelProperty(value = "行数")
    private Long rows;

    /**
     *
     */
    @ApiModelProperty(value = "当前页")
    private Long offset;

    /**
     *
     */
    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 状态: A 有效  B 隐藏  C 待审核 D 审核不通过 X 删除
     */
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 评价内容模糊搜索
     */
    @ApiModelProperty(value = "评价内容模糊搜索")
    private String comments;

    /**
     * 渠道标识，由于此接口要非登录状态下查询
     */
    @ApiModelProperty(value = "渠道标识")
    private Long channelId;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "用户ID")
    private String userId;
}
