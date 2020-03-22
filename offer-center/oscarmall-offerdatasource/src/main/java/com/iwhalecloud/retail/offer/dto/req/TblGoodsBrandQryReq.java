package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName TblGoodsBrandQryReq
 * @Author wangzhongbao
 * @Description
 * @Date 2019/5/14 14:57
 **/
@Data
public class TblGoodsBrandQryReq extends PageVO {

    private static final long serialVersionUID = 97051167768288620L;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "是否热门")
    private String isHot;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

}
