package com.iwhalecloud.retail.offer.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 赞踩
 * 
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Data
public class QueryUsefulUselessResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型 1 赞 2 踩")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
