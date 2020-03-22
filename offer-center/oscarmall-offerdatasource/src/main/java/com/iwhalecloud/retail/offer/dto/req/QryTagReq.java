package com.iwhalecloud.retail.offer.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QryTagReq implements Serializable {

    private static final long serialVersionUID = -4751765569601981963L;

    private List<String> tagIds;

    private String tenantId;
}
