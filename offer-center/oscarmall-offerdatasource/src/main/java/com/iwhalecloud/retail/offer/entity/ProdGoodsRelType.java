package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品关系类型(域)
 * @author fanxiaofei
 * @date 2019/02/26
 */
@Data
@TableName("tbl_prod_goods_rel_type")
public class ProdGoodsRelType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * ID
     */
    @TableId(type = IdType.INPUT)
    private String relType;

    /**
     * 名称
     */
    private String relTypeName;

    /**
     * 描述
     */
    private String comments;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}

