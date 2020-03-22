package com.iwhalecloud.retail.offer.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @version 1.0
 * @ClassName TblGoodsBrand
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/5/13 10:09
 **/
@Data
@TableName("tbl_goods_brand")
public class TblGoodsBrand implements Serializable {

    private static final long serialVersionUID = 4978203869694215728L;

    private Long id;

    @TableId(type = IdType.INPUT)
    private String brandId;

    private String brandName;

    private String brandImg;

    private String brandIntro;

    private String isHot;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;
    /**
     * A:正常 X:失效
     */
    private String state;

    private Date stateDate;

    private String storeId;

    private String tenantId;
}
