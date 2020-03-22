package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 4:30 PM
 */

@Data
@TableName("tbl_api_ability")
public class TblApiAbility implements Serializable {


    private static final long serialVersionUID = 8286294011538671889L;

    private Long id;

    private String apiAbilityId;

    private String apiAbilityName;

    private String apiAbilityCode;

    private String comments;

    private String state;

    private Date stateDate;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;


}
