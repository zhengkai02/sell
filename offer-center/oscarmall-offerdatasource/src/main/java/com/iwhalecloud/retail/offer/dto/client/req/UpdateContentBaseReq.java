package com.iwhalecloud.retail.offer.dto.client.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 更新内容 赞和踩
 * @author fanxiaofei
 * @date 2019-05-08
 */
@Data
public class UpdateContentBaseReq implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "赞")
    private Integer usefulCount;

    @ApiModelProperty(value = "踩")
    private Integer uselessCount;

    @ApiModelProperty(value = "A 新增 D 删除")
    private String action;

    @ApiModelProperty(value = "1 赞 2 踩")
    private String type;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

}

