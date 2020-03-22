package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author huminghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/8 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_price_factor")
public class PriceFactor implements Serializable {

    private static final long serialVersionUID = -1159679791916672307L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "价格因子标识")
    private Long factorId;

    @ApiModelProperty(value = "价格因子名称")
    private String factorName;

    @ApiModelProperty(value = "价格因子编码")
    private String factorCode;

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
