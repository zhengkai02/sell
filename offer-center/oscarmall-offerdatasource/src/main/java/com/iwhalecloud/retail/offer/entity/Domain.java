package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * <Description> <br> 
 *  
 * @author wang.zhongbao<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019年3月4日 <br>
 * @since V9.0C<br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 */
@Data
@TableName("tbl_domain")
public class Domain implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;
    
    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -556741045206989140L;

    /**
     * tableName
     */
    @ApiModelProperty(value = "表名")
    String tableName; 
    
    /**
     * columnName
     */
    @ApiModelProperty(value = "列名")
    String columnName; 
    
    /**
     * lookupName
     */
    @ApiModelProperty(value = "值")
    String value; 
    
    /**
     * lookupName
     */
    @ApiModelProperty(value = "检查名称")
    String lookupName; 
    
    /**
     * comments
     */
    @ApiModelProperty(value = "描述")
    String comments;

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
