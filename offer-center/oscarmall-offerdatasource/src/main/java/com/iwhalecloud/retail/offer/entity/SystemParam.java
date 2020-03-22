package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/14 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_system_param")
public class SystemParam implements Serializable {

    private static final long serialVersionUID = 4011785935899670099L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "参数")
    private Long param;

    @ApiModelProperty(value = "参数名称")
    private String paramName;

    @ApiModelProperty(value = "MASK")
    private String mask;

    @ApiModelProperty(value = "当前值")
    private String currentValue;

    @ApiModelProperty(value = "说明")
    private String comments;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "状态")
    private String state;
}
