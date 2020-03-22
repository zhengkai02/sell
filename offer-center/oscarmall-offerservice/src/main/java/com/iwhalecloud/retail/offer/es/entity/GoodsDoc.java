package com.iwhalecloud.retail.offer.es.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xh on 2019/5/16.
 *  目前索引的和搜索的都在es中了，需要优化下，只存索引字段，搜索信息再从数据库查询补全
 */
@Data
public class GoodsDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String sn;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 类型id  1.终端 2.套餐 3.配件 4.合约计划 5.其它 6.券类 7.内容会员 8 权益
     */
    private String typeId;

    /**
     * 状态 A 新建  B 待发布  C 已发布  X 失效
     */
    private String state;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 销售价
     */
    private Long price;


    /**
     *  税费
     */
    private Long tax;

    /**
     * 市场价
     */
    private Long mktprice;


    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 购买数
     */
    private Long buyCount = 0L;


    /**
     * 副标题
     */
    private String simpleName;


    /**
     * _商家_分类_品牌_商品名称_ search_key like %%
     */
    private String searchKey;


    /**
     * 上架时间
     */
    private Date marketingBeginTime;

    /**
     * 下架时间
     */
    private Date marketingEndTime;

    /**
     * 库存数量
     */
    private Long stockQty;


    /**
     * 缩略图
     */
    private String thumbnail;


    /**
     * 租户
     */
    private String tenantId;

    /**
     * 好评率
     **/
    private Integer evaluationRate = 0;

    /**
     * 评价数
     **/
    private Integer evaluationCount = 0;

    /**
     * 是否热销商品 Y是 N不是
     * 默认N
     */
    private String isHot;


    /**
     * 目录Ids
     */
    private String catIds;

    /**
    * 目录Names
    */
    private String attrValues;

    /**
     * isRecommend
     */
    private String isRecommend;
}
