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
 * @CreateDate 2019/5/29 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0<br>
 */
@Data
@TableName("tbl_goods_cat_brand")
public class TblGoodsCatBrand implements Serializable {

    private static final long serialVersionUID = 1943471946896068131L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String relaId;

    /**
     * 销售目录标识
     */
    private String catId;

    /**
     * 销售目录关联的品牌标识
     */
    private String brandId;

    /**
     * 租户标识
     */
    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;
}
