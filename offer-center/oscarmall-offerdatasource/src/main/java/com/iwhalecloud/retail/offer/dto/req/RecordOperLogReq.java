package com.iwhalecloud.retail.offer.dto.req;

import java.io.Serializable;
import java.util.List;

import com.iwhalecloud.retail.offer.entity.OperDetail;
import com.iwhalecloud.retail.offer.entity.OperLog;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.offer.dto.req <br>
 * @since V9.0C<br>
 */
@Data
public class RecordOperLogReq implements Serializable {

    private static final long serialVersionUID = 558167827313828282L;

    private String requestId;

    private OperLog operLog;

    private List<OperDetail> operDetailList;
}
