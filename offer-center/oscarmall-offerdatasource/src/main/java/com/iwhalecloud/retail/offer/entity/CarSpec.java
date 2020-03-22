package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author liuhongyu4 <br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/12 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since CRM <br>
 */
@Data
@TableName("tbl_car_spec")
public class CarSpec implements Serializable {

    @TableId(type = IdType.INPUT)
    private Integer id;

    private String carSpecId;

    private String carSpecName;

    private String carBrandId;

    private String parentId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
