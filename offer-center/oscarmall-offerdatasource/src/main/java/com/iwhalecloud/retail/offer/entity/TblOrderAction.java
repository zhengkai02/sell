package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 6:01 PM
 */
@Data
@TableName("tbl_order_action")
public class TblOrderAction implements Serializable {
    private static final long serialVersionUID = 5030838710967099438L;

    private Long id;

    private Long orderActionId;

    private String orderActionName;

    private String comments;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
