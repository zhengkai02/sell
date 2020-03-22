package com.iwhalecloud.retail.offer.dto.resp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandAddResp
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/13 10:34
 **/
@Data
public class TblGoodsBrandAddResp implements Serializable {

    @ApiModelProperty(value = "品牌图片")
    private String brandName;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "品牌图片")
    private String brandImg;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntro;

    @ApiModelProperty(value = "品牌url")
    private String brandUrl;

    @ApiModelProperty(value = "是否热门")
    private String isHot;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    /**
     * A:正常 X:失效
     */
    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

}
