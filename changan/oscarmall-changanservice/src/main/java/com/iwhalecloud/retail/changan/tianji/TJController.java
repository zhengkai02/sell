package com.iwhalecloud.retail.changan.tianji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengKai
 * @data 2019-11-21 12:27
 */
@RestController
@RequestMapping("/tianji")
public class TJController {

    @Autowired
    private OrderStateBackService orderStateBackService;

    @PostMapping("/order/pay/callBack")
    public ResultVO<ResponseDTO> orderPayCallBack(@RequestParam("orderId") String orderId, @RequestParam("payStatus") String payStatus){
        ResultVO<ResponseDTO> result = orderStateBackService.orderPayStateBack(orderId, payStatus);
        return result;
    }

    @PostMapping("/order/open/callBack")
    public ResultVO<ResponseDTO> orderOpenCallBack(@RequestParam("orderId") String orderId,@RequestParam("iccid") String iccid,@RequestParam("openStatus") String openStatus){

        ResultVO<ResponseDTO> result = orderStateBackService.orderOpenStateBack(orderId, iccid, openStatus);
        ResultVOUtils.success(result);
        return result;
    }
}
