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
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/30 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0<br>
 */
@Data
@TableName("tbl_prod_goods_cat_rela")
public class ProdGoodsCatRela implements Serializable {

    private static final long serialVersionUID = 684697222969340176L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String relaId;

    private String saleCatId;

    private String manageCatId;

    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
