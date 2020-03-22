package com.iwhalecloud.retail.offer.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.RecordOperLogReq;
import com.iwhalecloud.retail.offer.entity.OperDetail;
import com.iwhalecloud.retail.offer.entity.OperLog;
import com.iwhalecloud.retail.offer.mapper.OperDetailMapper;
import com.iwhalecloud.retail.offer.mapper.OperLogMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/26 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OperLogManager {

    @Autowired
    private OperDetailMapper operDetailMapper;

    @Autowired
    private OperLogMapper operLogMapper;

    private void releaseForLog(List<OperDetail> operDetails) {
        if (CollectionUtils.isNotEmpty(operDetails)) {
            operDetailMapper.batchInsertOperDetail(operDetails);
        }
    }

    public void recordOperLog(RecordOperLogReq request) throws BaseException {
        log.info("OperLogManager recordOperLog start, request = [{}]", request);

        if (null == request) {
            return;
        }

        AssertUtil.isNotNull(request.getOperLog(), OfferBaseMessageCodeEnum.PARAM_ERROR);
        OperLog operLog = request.getOperLog();
        AssertUtil.isNotNull(operLog.getUserId(), OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(operLog.getIpAddr(), OfferBaseMessageCodeEnum.PARAM_ERROR);

        if (null == operLog.getOperLogId()) {
            operLog.setOperLogId(UidGeneator.getUID());
        }

        operLogMapper.insert(operLog);

        if (CollectionUtils.isNotEmpty(request.getOperDetailList())) {
            List<OperDetail> operDetails = new ArrayList<>();
            List<OperDetail> operDetailList = request.getOperDetailList();
            for (OperDetail operDetail : operDetailList) {
                try {
                    AssertUtil.isNotEmpty(operDetail.getTableName(), OfferBaseMessageCodeEnum.PARAM_ERROR);
                    AssertUtil.isNotEmpty(operDetail.getColumnName(), OfferBaseMessageCodeEnum.PARAM_ERROR);
                    AssertUtil.isNotNull(operDetail.getPkId(), OfferBaseMessageCodeEnum.PARAM_ERROR);

                    if (null == operDetail.getOperDetailId()) {
                        operDetail.setOperDetailId(UidGeneator.getUID());
                    }
                    if (null == operDetail.getOperLogId()) {
                        operDetail.setOperLogId(operLog.getOperLogId());
                    }
                    operDetails.add(operDetail);
                }
                catch (Exception e) {
                    log.error("operDetail error, operDetail = [{}], exception = [{}]", operDetail, e);
                }

            }
            releaseForLog(operDetails);
        }

        log.info("OperLogManager recordOperLog end");
    }



}
