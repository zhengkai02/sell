package indi.zk.mall.order.tianji;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 流量商城订单支付、开通状态回调接口
 *
 * @author adh 201911114
 */
@Service
public class OrderStateBackServiceImpl implements OrderStateBackService {
    SimpleDateFormat simDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String tokenKey = "TrafficMallMD5Secret";//加密密钥

    /**
     * TSOP商城订单支付状态回调流量商城接口
     *
     * @param orderId   String TSOP商城订单ID
     * @param payStatus String 支付状态 1成功 2失败
     * @return String (0:成功，1：失败)
     */
    public ResultVO<ResponseDTO> orderPayStateBack(String orderId, String payStatus) {
        Date date1 = new Date();
        String startDate = simDate.format(date1);
        String url = null;
        if (url == null || "".equals(url)) {
            url = "http://tj.test.obc.com/api/order/orderPayStatusBack";
        }
        Map<String, Object> orderPayStatusMsg = new HashMap();
        orderPayStatusMsg.put("orderId", orderId);
        orderPayStatusMsg.put("payStatus", payStatus);
        orderPayStatusMsg.put("time", System.currentTimeMillis() + "");
        //获取签名信息
        String sign = MD5Util.doSign(orderPayStatusMsg, tokenKey);
        Map<String, Object> requestMap = new HashMap();
        requestMap.put("sign", sign);
        requestMap.put("orderPayStatusMsg", orderPayStatusMsg);
        HttpSendRequst httpSendRequst = new HttpSendRequst();
        try {
            Map resultMap = httpSendRequst.sendRequst(url, requestMap);
            if (StringUtils.isEmpty(requestMap)) {
                throw new Exception("天际服务器未响应");
            }
            String responseBody = resultMap.get("responseBody").toString();
            ResponseDTO responseDTO = JSONUtil.getPOJOFromJSONString(responseBody, ResponseDTO.class);
            String code = responseDTO.getCode();
            if ("001_0000".equals(code)) {
                return ResultVOUtils.success(responseBody);
            } else if ("000_0001".equals(code)) {
                return ResultVOUtils.fail(responseDTO.getCode(), responseDTO.getMessage(), responseDTO);
            }
            return ResultVOUtils.fail(responseDTO.getCode(), responseDTO.getMessage(), responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtils.fail(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(), CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage(), "调用天际系统异常");
    }

    /**
     * TSOP商城订单开通状态回调流量商城接口
     *
     * @param orderId    String TSOP商城订单ID
     * @param iccid      String iccid
     * @param openStatus String 开通状态 1成功 2失败
     * @return String (0:成功，1：失败)
     */
    public ResultVO<ResponseDTO> orderOpenStateBack(String orderId, String iccid, String openStatus) {
        String returnStr = "0";//返回值
        Date date1 = new Date();
        String startDate = simDate.format(date1);
        System.out.println("===开通状态回调：date:" + startDate + "=orderId:" + orderId + "=iccid:" + iccid + "=openStatus:" + openStatus);
        String url = null;
        if (url == null || "".equals(url)) {
            url = "http://tj.test.obc.com/api/order/orderOpenStatusBack";
        }
        try {
            Map<String, Object> orderOpenStatusMsg = new HashMap();
            orderOpenStatusMsg.put("orderId", orderId);
            orderOpenStatusMsg.put("iccid", iccid);
            orderOpenStatusMsg.put("openStatus", openStatus);
            orderOpenStatusMsg.put("time", System.currentTimeMillis() + "");
            //获取签名信息
            String sign = MD5Util.doSign(orderOpenStatusMsg, tokenKey);
            Map<String, Object> requestMap = new HashMap();
            requestMap.put("sign", sign);
            requestMap.put("orderOpenStatusMsg", orderOpenStatusMsg);
            System.out.println("===sign:" + sign);
            //System.out.println("===requestMap:"+requestMap);
            HttpSendRequst httpSendRequst = new HttpSendRequst();
            Map resultMap = httpSendRequst.sendRequst(url, requestMap);
            String responseBody = resultMap.get("responseBody").toString();
            ResponseDTO responseDTO = JSONUtil.getPOJOFromJSONString(responseBody, ResponseDTO.class);
            String code = responseDTO.getCode();
            if ("001_0000".equals(code)) {
                return ResultVOUtils.success(responseDTO);
            } else if ("000_0001".equals(code)) {
                return ResultVOUtils.fail(responseDTO.getCode(), responseDTO.getMessage(), responseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtils.fail(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(), CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage(), "调用天际系统异常");
    }

}
