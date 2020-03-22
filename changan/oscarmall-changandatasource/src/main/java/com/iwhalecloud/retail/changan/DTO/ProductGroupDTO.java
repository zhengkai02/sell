package com.iwhalecloud.retail.changan.DTO;

import lombok.Data;

/**
 * @author ZhengKai
 * @data 2019-11-07 14:03
 */
@Data
public class ProductGroupDTO {

    /** 产品分类ID. */
    private String groupId;

    /** 产品分类名称. */
    private String groupName;

    /** 产品分类编码. */
    private String GroupCode;

    /** 产品分类父节点ID. */
    private String pid;
}
