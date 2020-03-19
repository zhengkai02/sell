package indi.zk.mall.order.tianji;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.util.HashMap;
import java.util.Map;


public class HttpSendRequst {
	/**
	 * Http调用接口公共方法
	 * @param url
	 * @param map
	 * @return
	 */
	public Map sendRequst(String url, Map map){
		PostMethod postm = null;
	    
	    Map resultMap = new HashMap();
		try {
			HttpClient httpclient = new HttpClient();	
			httpclient.setTimeout(20000);
			httpclient.setConnectionTimeout(20000);
			postm = new PostMethod(url);
		    postm.addRequestHeader("Accept" , "application/json");
			String json = JSONUtil.getJSONStringFromPOJO(map);
		    RequestEntity requestEntity = new StringRequestEntity(json,"application/json",null);
		    postm.setRequestEntity(requestEntity);
		    int responseCode = httpclient.executeMethod(postm);
		    String responseBody = postm.getResponseBodyAsString();
		    resultMap.put("responseCode", responseCode+"");
		    resultMap.put("responseBody", responseBody);
		    
	    } catch(Exception e){
			e.printStackTrace();
	    } finally {
	    	if(postm!=null){
	    		postm.releaseConnection();
	    	}
	    }
	    return resultMap;
	}
}
