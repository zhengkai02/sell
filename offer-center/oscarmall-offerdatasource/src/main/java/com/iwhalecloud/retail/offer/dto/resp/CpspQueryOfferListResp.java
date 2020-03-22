package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fanxiaofei
 * @date 2019-07-30
 */
@Data
public class CpspQueryOfferListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品列表 M")
    private List<CpspQueryOfferResp> offerList;

    @ApiModelProperty(value = "每页显示条数 M")
    private Long pageSize;

    @ApiModelProperty(value = "当前页 M")
    private Long pageNo;

    @ApiModelProperty(value = "总条数 M")
    private Long totalCount;

    @ApiModelProperty(value = "总页数 M")
    private Long totalPage;

}
