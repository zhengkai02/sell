package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName TblStoreCatg
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/4/28 15:27
 **/
@Data
@TableName("tbl_store_catg")
public class TblStoreCatg implements Serializable {

    private static final long serialVersionUID = 4011785935899670099L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String catId;

    private String name;

    private String comments;

    private String storeId;

    private String parentId;

    private String logo;

    private Long catOrder;

    private Date createTime;

    private String tenantId;

    private String createBy;

    private String modifyBy;

    private Date modifyTime;

    private String state;

}
