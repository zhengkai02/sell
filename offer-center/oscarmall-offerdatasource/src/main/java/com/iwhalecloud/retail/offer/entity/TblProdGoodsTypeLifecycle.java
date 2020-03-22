package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzhang
 * @CreateDate 2019/8/26 6:06 PM
 */
@Data
@TableName("tbl_prod_goods_type_lifecycle")
public class TblProdGoodsTypeLifecycle implements Serializable {

    private static final long serialVersionUID = 3127654808602762536L;

    private Long id;

    private Long lifecycleId;

    private Long typeId;

    private Long srcOrderState;

    private Long orderActionId;

    private Long objOrderState;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
