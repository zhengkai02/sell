package com.iwhalecloud.retail.changan.tianji;

/**
 * @author ZhengKai
 * @data 2019-11-21 11:25
 */
public interface OrderStateBackService {

    ResultVO<ResponseDTO> orderPayStateBack(String orderId, String payStatus);

    ResultVO<ResponseDTO> orderOpenStateBack(String orderId, String iccid, String openStatus);

}
