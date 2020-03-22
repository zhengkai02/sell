package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 4:48 PM
 */
@Data
@TableName("tbl_api_ip_whitelist")
public class TblApiIpWhitelist implements Serializable {

    private static final long serialVersionUID = -8369517049476849575L;

    private Long id;

    private String ipWhitelistId;

    private String apiAbilityId;

    private String startIpAddress;

    private String endIpAddress;

    private String state;

    private Date stateDate;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

}
