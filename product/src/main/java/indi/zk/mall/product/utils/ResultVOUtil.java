package indi.zk.mall.product.utils;

import indi.zk.mall.product.VO.ResultVO;

/**
 * @author ZhengKai
 * @data 2019-09-15 12:03
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
