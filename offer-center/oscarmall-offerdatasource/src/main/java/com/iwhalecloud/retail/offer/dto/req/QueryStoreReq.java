package com.iwhalecloud.retail.offer.dto.req;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺
 * 
 * @author fanxiaofei
 * @date 2019-04-29
 */
@Data
@TableName("tbl_store")
public class QueryStoreReq extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "店铺级别")
    private Long storeLevelId;

    @ApiModelProperty(value = "状态 A 正常 E 已关闭/已封闭")
    private String state;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;
}
