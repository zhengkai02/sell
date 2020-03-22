package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.CmWsResponse;
import com.iwhalecloud.retail.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/15 <br>
 * @see com.iwhalecloud.retail.order.utils <br>
 * @since V9.0C<br>
 */
@Slf4j
public abstract class CmWsResponseCheckUtil {

    private CmWsResponseCheckUtil() { }

    private static final String COMMON_SUCCESS = "0000_0";

    public static void checkResponse(CmWsResponse wsResponse) throws BaseException {
        log.info("checkRespons, wsResponse = [{}]", wsResponse);
        if (wsResponse == null || StringUtils.isEmpty(wsResponse.getStatus())) {
            throw new BaseException(CommonBaseMessageCodeEnum.SYSTEM_ERROR);
        }
        log.info(wsResponse.toString());
        String code = wsResponse.getStatus();

        if (!code.equals(COMMON_SUCCESS)) {
            List<String> messages =  wsResponse.getMessages();
            if (CollectionUtils.isNotEmpty(messages) && StringUtils.isNotEmpty(messages.get(0))) {
                throw new BaseException(code, messages.get(0));
            }
        }
    }
}
