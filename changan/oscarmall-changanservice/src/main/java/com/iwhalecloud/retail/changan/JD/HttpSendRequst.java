package com.iwhalecloud.retail.changan.JD;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;


public class HttpSendRequst {
	/**
	 * Http调用接口公共方法
	 * @param url
	 * @param map
	 * @return
	 */
	public Map sendRequst(String url, Map map){
		System.out.println("==========调用http接口开始url:"+url);
		System.out.println("==========map:"+map);
		PostMethod postm = null;
	    
	    Map resultMap = new HashMap();
		try {
			HttpClient httpclient = new HttpClient();	
			httpclient.setTimeout(20000);
			httpclient.setConnectionTimeout(20000);
			
			System.out.println("---------------1");
			postm = new PostMethod(url);
		    postm.addRequestHeader("Accept" , "application/json");
		    System.out.println("---------------2");
			String json = JSONUtil.getJSONStringFromPOJO(map);
		    RequestEntity requestEntity = new StringRequestEntity(json,"application/json",null);
		    postm.setRequestEntity(requestEntity);
		    System.out.println("---------------3");
		    int responseCode = httpclient.executeMethod(postm);
		    System.out.println("Response status code: " + responseCode);
		    
		    String responseBody = postm.getResponseBodyAsString();
		    System.out.println("Response body: ");
		    System.out.println(responseBody);
		    
		    
		    resultMap.put("responseCode", responseCode+"");
		    resultMap.put("responseBody", responseBody);
		    
	    } catch(Exception e){
	    	System.out.println("==============调用http接口异常!");
	    	System.out.println("HttpSendRequst异常信息："+e.getMessage());
	    } finally {
	    	if(postm!=null){
	    		postm.releaseConnection();
	    	}
	    }
	    System.out.println("==========调用http接口结束!");
	    return resultMap;
	}
}
