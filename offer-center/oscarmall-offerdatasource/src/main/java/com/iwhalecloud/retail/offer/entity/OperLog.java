package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_oper_log")
public class OperLog implements Serializable {

    private static final long serialVersionUID = -2085822161201515316L;

    private Long id;

    @TableId(type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long operLogId;

    private Long privilegeId;

    private Long userId;

    private String operInfo;

    private Long operEvent;

    private String operType;

    private String refObjId;

    private String ipAddr;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
