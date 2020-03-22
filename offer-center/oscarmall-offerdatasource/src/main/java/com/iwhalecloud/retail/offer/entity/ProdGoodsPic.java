package com.iwhalecloud.retail.offer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @ClassName ProdGoodsPic
 * @Author wangzhongbao
 * @Description //TODO
 * @Date 2019/3/21 15:52
 **/
@Data
@TableName("tbl_prod_goods_pic")
public class ProdGoodsPic implements Serializable {

    private static final long serialVersionUID = 8647622555665848920L;

    private Long id;

    private String goodsPicId;

    private String goodsId;

    private String picType;

    private String picUrl;

    private Long priority;

    private String state;

    private Date stateDate;

    private Date createTime;

    private String createBy;

    private String modifyBy;

    private Date modifyTime;

    private String tenantId;



}
