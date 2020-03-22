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
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
@TableName("tbl_guard_message_rule")
public class TblGuardMessageRule implements Serializable {

    private static final long serialVersionUID = 6150260466759449079L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String queue;

    /**
     * Y:可以降级 N:不可以降级
     */
    private String canDowngrade;

    /**
     * 重试次数，不可以降级时必须填写，不填时默认值为1
     */
    private Long retryNum;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
