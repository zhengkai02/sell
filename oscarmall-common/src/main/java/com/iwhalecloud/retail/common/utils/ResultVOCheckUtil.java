package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.consts.BaseMessageIntf;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * <Description> <br>
 *
 * @author yang.bo27<br>
 * @version 1.0 <br>
 * @taskId <br>
 * @CreateDate 2019/3/8 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since IOT <br>
 */
@Slf4j
public abstract class ResultVOCheckUtil  {
    private ResultVOCheckUtil() { }

    private static final int ARRAY_LENGTH = 3;
    private static final int ARRAY_NUM = 2;

    private static final String BASE_INFO = "BaseException = [{}]";
    private static final String E_INFO = "Exception = [{}]";

    public static void checkResultVO(ResultVO resultVO) throws BaseException {
        log.info("checkResultVO, resultVO = [{}]", resultVO);
        if (resultVO == null || StringUtils.isEmpty(resultVO.getCode())) {
            throw new BaseException(CommonBaseMessageCodeEnum.SYSTEM_ERROR);
        }
        log.info(resultVO.toString());
        String code = resultVO.getCode();

        String[] codeArray = code.split("_");
        if (codeArray.length == ARRAY_LENGTH) {
            code = codeArray[ARRAY_NUM];
        }

        if (!"0000".equals(code)) {
            throw new BaseException(resultVO.getCode(), resultVO.getMessage());
        }
    }

    public static void checkResultVOData(ResultVO resultVO, String message) throws BaseException {
        checkResultVO(resultVO);
        if (resultVO.getData() == null) {
            throw new BaseException(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(), message);
        }
    }

    public static void checkResultVOData(ResultVO resultVO, BaseMessageIntf error) throws BaseException {
        checkResultVO(resultVO);
        if (resultVO.getData() == null) {
            throw new BaseException(error);
        }
    }

    public static <T, R extends Serializable> ResultVO<R> buildResultVO(CustomFunction<T, R> function, T param) {
        ResultVO<R> resultVO = new ResultVO();
        try {
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            resultVO.setData(function.apply(param));
        }
        catch (BaseException e) {
            log.error(BASE_INFO, e);
            resultVO.setCode(e.getCode());
            resultVO.setMessage(e.getDesc());
        }
        catch (Exception e) {
            log.error(E_INFO, e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }
        return resultVO;
    }

    public static <T> ResultVO buildResultVONoReturn(CustomNoReturnFunction<T> function, T param) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            function.apply(param);
        }
        catch (BaseException e) {
            log.error(BASE_INFO, e);
            resultVO.setCode(e.getCode());
            resultVO.setMessage(e.getDesc());
        }
        catch (Exception e) {
            log.error(E_INFO, e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }
        return resultVO;
    }

    public static <R extends Serializable> ResultVO<R> buildResultVO(CustomNoParamFunction<R> function) {
        ResultVO<R> resultVO = new ResultVO();
        try {
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            resultVO.setData(function.apply());
        }
        catch (BaseException e) {
            log.error(BASE_INFO, e);
            resultVO.setCode(e.getCode());
            resultVO.setMessage(e.getDesc());
        }
        catch (Exception e) {
            log.error(E_INFO, e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }
        return resultVO;
    }
}
