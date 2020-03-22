package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/4/1 <br>
 * @see com.iwhalecloud.retail.operation.dto <br>
 * @since IOT <br>
 */
@TableName("tbl_tmp_user")
@Data
public class TmpUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String userCode;

    private String password;

    private String userName;

    private String roles;

    private String tenantId;

    private String storeId;
}
