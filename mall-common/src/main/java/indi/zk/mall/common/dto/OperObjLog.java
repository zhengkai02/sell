package com.iwhalecloud.retail.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/28 <br>
 * @see com.iwhalecloud.retail.common.dto <br>
 * @since V9.0C<br>
 */
@Data
public class OperObjLog implements Serializable {

    private static final long serialVersionUID = 4912864857674643056L;

    private Long operLogId;

    private Long privilegeId;

    private Long userId;

    private String createDate;

    private String operInfo;

    private Long operEvent;

    private String operType;

    private String refObjId;

    private String ipAddr;
}
