package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 6:12 PM
 */

@Data
@TableName("tbl_store_catg_rela")
public class TblStoreCatgRela implements Serializable {

    private static final long serialVersionUID = -2516925634793384842L;

    private Long id;

    private String relaId;

    private String storeCatId;

    private String saleCatId;

    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
