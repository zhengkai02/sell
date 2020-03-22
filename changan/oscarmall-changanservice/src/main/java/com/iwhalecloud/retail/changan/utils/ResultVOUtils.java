package com.iwhalecloud.retail.changan.utils;

import com.iwhalecloud.retail.changan.VO.OfferToJDResultVO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-11-06 14:43
 */
public class ResultVOUtils {
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
        resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail(String code, String message, Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(data);
        return resultVO;

    }

    /**
     * 捷达系统返回成功
     * @author wamghw
     * @data 2019-11-18
     */
    public static OfferToJDResultVO success(String status, String[] messages, List result){
        OfferToJDResultVO resultVO = new OfferToJDResultVO();
        resultVO.setStatus(status);
        resultVO.setMessages(messages);
        resultVO.setResult(result);
        return resultVO;
    }

    public static OfferToJDResultVO success(String status, String[] messages){
        OfferToJDResultVO resultVO = new OfferToJDResultVO();
        resultVO.setStatus(status);
        resultVO.setMessages(messages);
        return resultVO;
    }

    //捷达系统未响应
    public static OfferToJDResultVO fail(String status, String[] messages){
        OfferToJDResultVO resultVO = new OfferToJDResultVO();
        resultVO.setStatus(status);
        resultVO.setMessages(messages);
        return resultVO;
    }
}
