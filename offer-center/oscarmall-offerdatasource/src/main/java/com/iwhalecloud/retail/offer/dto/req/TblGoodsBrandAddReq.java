package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.Date;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandAddReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/13 10:39
 **/
@Data
public class TblGoodsBrandAddReq implements Serializable {

    private static final long serialVersionUID = 7344747142692222674L;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "品牌图片")
    private String brandName;

    @ApiModelProperty(value = "品牌图片")
    private String brandImg;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntro;

    @ApiModelProperty(value = "是否热门")
    private String isHot;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;
    /**
     * A:正常 X:失效
     */
    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "状态时间")
    private Date stateDate;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "用户信息")
    private UserDTO tmpUserDTO;

}
