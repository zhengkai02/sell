package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName QueryOrgRsp
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/5/29 16:33
 **/
@Data
public class QueryOrgRsp implements Serializable {

    private static final long serialVersionUID = 5618260528264969479L;

    @ApiModelProperty(value = "机构ID")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

}
