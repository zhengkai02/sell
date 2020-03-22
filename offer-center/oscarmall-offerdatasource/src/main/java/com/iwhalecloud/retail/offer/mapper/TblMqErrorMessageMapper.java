package com.iwhalecloud.retail.offer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.QryErrorMqMessageReq;
import com.iwhalecloud.retail.offer.dto.resp.QryErrorMqMessageResp;
import com.iwhalecloud.retail.offer.entity.TblMqErrorMessage;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/7 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface TblMqErrorMessageMapper extends BaseMapper<TblMqErrorMessage> {

    void insertErrorMessage(TblMqErrorMessage tblMqErrorMessage);

    void retryFail(TblMqErrorMessage tblMqErrorMessage);

    Page<QryErrorMqMessageResp> qryErrorMqMessage(Page<QryErrorMqMessageResp> page, @Param("req") QryErrorMqMessageReq req);

    void deleteMqErrorMessage(@Param("messageId") String messageId, @Param("modfiyBy") String modfiyBy);

    void resendMqErrorMessage(@Param("messageId") String messageId, @Param("modfiyBy") String modfiyBy);

    void finishErrorMessage(@Param("messageId") String messageId, @Param("modifyBy") String modifyBy);
}
