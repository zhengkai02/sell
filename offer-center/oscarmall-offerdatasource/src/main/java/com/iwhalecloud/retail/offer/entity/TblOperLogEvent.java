package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 4:54 PM
 */


@Data
@TableName("tbl_oper_log_event")
public class TblOperLogEvent implements Serializable {

    private static final long serialVersionUID = 3743820154335967170L;

    private Long id;

    private Long eventId;

    private String eventName;

    private String comments;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
