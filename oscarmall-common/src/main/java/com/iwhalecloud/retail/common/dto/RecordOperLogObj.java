package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.common.dto <br>
 * @since V9.0C<br>
 */
@Data
public class RecordOperLogObj implements Serializable {

    private static final long serialVersionUID = 558167827313828282L;

    private String requestId;

    private OperObjLog operLog;

    private List<OperObjDetail> operDetailList;
}
