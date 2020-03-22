package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName TblStoreCatgMem
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/4/29 15:36
 **/
@Data
@TableName("tbl_store_catg_mem")
public class TblStoreCatgMem implements Serializable {

    private static final long serialVersionUID = -6402717749983483340L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String catMemId;

    private String catId;

    private String goodsId;

    private String tenantId;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
