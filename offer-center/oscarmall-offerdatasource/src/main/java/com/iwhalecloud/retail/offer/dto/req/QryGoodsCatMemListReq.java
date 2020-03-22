package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/1 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class QryGoodsCatMemListReq extends PageVO {

    @ApiModelProperty(value = "目录ID")
    private String catId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String sn;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "店铺ID")
    private String storeId;

    @ApiModelProperty(value = "上架时间")
    private Date marketingBeginTime;

    @ApiModelProperty(value = "下架时间")
    private Date marketingEndTime;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "目录类型")
    private String catType;
}
