package com.iwhalecloud.retail.offer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <Description> <br>
 *
 * @author li.sunlong01<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/13 <br>
 * @see com.iwhalecloud.retail.management.client.offer.dto.req <br>
 * @since V9.0<br>
 */
@Data
public class ProdGoodsEvaluationQryReq extends AbstractPageReq {

    private static final long serialVersionUID = -6565559926260327372L;
    /**
     * 商品名称模糊查询
     */
    @ApiModelProperty(value = "商品名称模糊查询")
    private String name;

    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    /**
     * 店铺创建时间
     */
    @ApiModelProperty(value = "店铺创建时间")
    private Date beginTime;

    /**
     * 店铺关闭时间
     */
    @ApiModelProperty(value = "店铺关闭时间")
    private Date endTime;

    /**
     * 评价等级
     */
    @ApiModelProperty(value = "评价等级")
    private Integer rate;
}
