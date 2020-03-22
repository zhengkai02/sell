package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.offer.entity <br>
 * @since V9.0C<br>
 */
@Data
public class OperObjDetail implements Serializable {

    private static final long serialVersionUID = -7038646552580851927L;

    /**
     * 商品id
     */
    private Long operDetailId;

    private Long operLogId;

    private String tableName;

    private String columnName;

    private Long pkId;

    private String oldValue;

    private String newValue;

}
