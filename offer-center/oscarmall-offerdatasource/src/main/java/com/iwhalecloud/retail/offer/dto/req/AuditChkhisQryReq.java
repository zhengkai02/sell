package com.iwhalecloud.retail.offer.dto.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuditChkhisQryReq implements Serializable {

    private static final long serialVersionUID = -7344497393114575672L;

    private String objId;

    private String objType;
}
