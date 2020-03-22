package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanxiaofei
 * @date 2019-03-14
 */
@Data
public class RightsGoodsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    private String offerId;

    @ApiModelProperty(value = "商品名称")
    private String offerName;

    @ApiModelProperty(value = "领取周期：0-天、1-年、2-月、3-周、4-季度")
    private Integer getCycle;

    @ApiModelProperty(value = "周期次数（如果周期是2周，则 cycleTime=2，getCycle=3）")
    private Integer cycleTimes;

    @ApiModelProperty(value = "总数")
    private Integer sumCount;

    @ApiModelProperty(value = "剩余领取次数")
    private Integer remainCount;

    @ApiModelProperty(value = "发放方式：0直接发放、1领取发放")
    private Integer giveWay;

    @ApiModelProperty(value = "商品缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "副标题")
    private String subhead;

    @ApiModelProperty(value = "库存")
    private String stockQty;

    @ApiModelProperty(value = "商品编码")
    private String sn;
}
