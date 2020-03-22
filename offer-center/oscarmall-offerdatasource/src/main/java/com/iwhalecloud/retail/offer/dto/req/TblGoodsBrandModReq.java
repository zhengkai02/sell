package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;

import com.iwhalecloud.retail.common.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandModReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/14 15:25
 **/
@Data
public class TblGoodsBrandModReq implements Serializable {

    private static final long serialVersionUID = -1792125501627935997L;


    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌图片")
    private String brandImg;

    @ApiModelProperty(value = "品牌介绍")
    private String brandIntro;

    @ApiModelProperty(value = "是否热门")
    private String isHot;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    /**
     * A:正常 X:失效
     */
    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "用户信息")
    private UserDTO tmpUserDTO;

}
