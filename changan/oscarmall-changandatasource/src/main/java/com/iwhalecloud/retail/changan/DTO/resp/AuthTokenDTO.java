package com.iwhalecloud.retail.changan.DTO.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhengKai
 * @data 2019-11-15 17:25
 */
@Data
public class AuthTokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String access_token;

    private String token_type;

    private long expires_in;

    private String scope;
}
