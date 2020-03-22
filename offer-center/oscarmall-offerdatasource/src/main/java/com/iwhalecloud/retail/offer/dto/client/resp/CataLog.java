package com.iwhalecloud.retail.offer.dto.client.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/2/15 <br>
 * @see com.iwhalecloud.retail.content.entity <br>
 * @since CRM <br>
 */
@Data
public class CataLog implements Serializable {

    /**
     * 目录id
     */
    @ApiModelProperty(value = "目录id")
    private Long cataId;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String name;

    /**
     * 上级目录
     */
    @ApiModelProperty(value = "上级目录")
    private Long parentCataId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String modifyBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

}
