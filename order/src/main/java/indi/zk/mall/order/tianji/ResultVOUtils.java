package indi.zk.mall.order.tianji;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;

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
}
