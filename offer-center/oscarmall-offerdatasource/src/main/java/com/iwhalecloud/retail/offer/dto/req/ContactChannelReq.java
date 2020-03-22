package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.common.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接触渠道
 * 
 * @author huminghang
 * @date 2019-06-14
 */
@Data
public class ContactChannelReq extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称")
    private String contactChannelName;

    /**
     * 渠道编码
     */
    @ApiModelProperty(value = "渠道编码")
    private String channelCode;

}
