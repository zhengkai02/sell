package com.iwhalecloud.retail.changan.JD;


public class test1 {
	public static void main(String[] args) {
		OrderStateBackInterface os = new OrderStateBackInterface();
		//支付状态回调
		String url = "http://tj.test.obc.com/api/order/orderPayStatusBack";
		String orderId = "123";
		String payStatus = "1";
		
		String a = os.orderPayStateBack(url, orderId, payStatus);
		
		System.out.println("===接口调用结果(0:成功，1：失败)："+a);
		
		//开通状态回调
		/*String url2 = "http://tj.test.obc.com/api/order/orderOpenStatusBack";
		String orderId2 = "123";
		String iccid = "123";
		String openStatus = "1";
		
		os.orderOpenStateBack(url2, orderId2, iccid, openStatus);*/
		
		
	}
}
