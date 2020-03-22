package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评价计算日志
 * 
 * @author fanxiaofei
 * @date 2019-05-07
 */
@Data
public class PageEvaluationLogReq extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价对象 A 商品, B 内容")
    private String objType;

    @ApiModelProperty(value = "对象ID")
    private String objId;
}
