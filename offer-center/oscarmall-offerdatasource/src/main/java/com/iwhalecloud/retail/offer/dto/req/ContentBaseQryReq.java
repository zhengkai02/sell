package com.iwhalecloud.retail.offer.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ContentBaseQryReq implements Serializable {
    private static final long serialVersionUID = 9134645190653042873L;

    private List<String> contentIds;

    /**
     * 根据title模糊查询
     */
    private String title;

    /**
     * 可选参数，一般根据唯一字段查询的话，用不到
     */
    private String tenantId;
}
