package com.iwhalecloud.retail.changan.JD;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

/**
 * 流量商城订单支付、开通状态回调接口 
 * @author adh 201911114
 *
 */
public class OrderStateBackInterface {
	SimpleDateFormat simDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String tokenKey = "TrafficMallMD5Secret";//加密密钥
	
	/**
	 * TSOP商城订单支付状态回调流量商城接口
	 * @param	url	String 接口服务端地址
	 * @param	orderId	String TSOP商城订单ID
	 * @param	payStatus	String 支付状态 1成功 2失败
	 * @return String (0:成功，1：失败)
	 */
	public String orderPayStateBack(String url, String orderId, String payStatus){
		String returnStr = "0";//返回值
		
		Date date1 = new Date();
		String startDate=simDate.format(date1);
		System.out.println("===支付状态回调：date:"+startDate+"=orderId:"+orderId+"=payStatus:"+payStatus);
		
		if(url==null || "".equals(url)){
			url = "http://tj.test.obc.com/api/order/orderPayStatusBack";
		}
		
		try{
			Map<String, Object> orderPayStatusMsg = new HashMap();
			orderPayStatusMsg.put("orderId", orderId);
			orderPayStatusMsg.put("payStatus", payStatus);
			orderPayStatusMsg.put("time", System.currentTimeMillis() + "");
			
			//获取签名信息
			String sign = MD5Util.doSign(orderPayStatusMsg, tokenKey);
			
			Map<String, Object> requestMap = new HashMap();
			requestMap.put("sign", sign);
			requestMap.put("orderPayStatusMsg", orderPayStatusMsg);
			
			System.out.println("===sign:"+sign);
	        //System.out.println("===requestMap:"+requestMap);
	        
	        HttpSendRequst httpSendRequst = new HttpSendRequst();
	        Map resultMap = httpSendRequst.sendRequst(url, requestMap);
	        
	        String responseBody = resultMap.get("responseBody").toString();
	        
	        
	        ResponseDto responseDto = JSONUtil.getPOJOFromJSONString(responseBody, ResponseDto.class);
	        String code = responseDto.getCode();
	        if(!"001_0000".equals(code)){
	        	returnStr = "1";
	        }
	        
        
		}catch(Exception e){
			System.out.println("==============orderPayStateBack异常!");
			System.out.println(e.getMessage());
		}
		
		return returnStr;
	}
	
	/**
	 * TSOP商城订单开通状态回调流量商城接口
	 * @param	url	String 接口服务端地址
	 * @param	orderId	String TSOP商城订单ID
	 * @param	iccid	String iccid
	 * @param	openStatus	String 开通状态 1成功 2失败
	 * @return String (0:成功，1：失败)
	 */
	public String orderOpenStateBack(String url, String orderId, String iccid, String openStatus){
		String returnStr = "0";//返回值
		
		Date date1 = new Date();
		String startDate=simDate.format(date1);
		System.out.println("===开通状态回调：date:"+startDate+"=orderId:"+orderId+"=iccid:"+iccid+"=openStatus:"+openStatus);
		
		if(url==null || "".equals(url)){
			url = "http://tj.test.obc.com/api/order/orderOpenStatusBack";
		}
		
		try{
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
			
			System.out.println("===sign:"+sign);
	        //System.out.println("===requestMap:"+requestMap);
	        
	        HttpSendRequst httpSendRequst = new HttpSendRequst();
	        Map resultMap = httpSendRequst.sendRequst(url, requestMap);
	        
	        String responseBody = resultMap.get("responseBody").toString();
	        
	        
	        ResponseDto responseDto = JSONUtil.getPOJOFromJSONString(responseBody, ResponseDto.class);
	        String code = responseDto.getCode();
	        if(!"001_0000".equals(code)){
	        	returnStr = "1";
	        }
	        
        
		}catch(Exception e){
			System.out.println("==============orderOpenStatusMsg异常!");
			System.out.println(e.getMessage());
		}
		
		return returnStr;
	}
	
}
