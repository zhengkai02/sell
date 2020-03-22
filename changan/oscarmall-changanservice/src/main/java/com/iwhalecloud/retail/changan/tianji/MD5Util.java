package com.iwhalecloud.retail.changan.tianji;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

public class MD5Util {
    public MD5Util() {
    }

    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    public static byte[] getContentBytes(String content, String charset) {
        if(charset != null && !"".equals(charset)) {
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException var3) {
                throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        } else {
            return content.getBytes();
        }
    }

    public static String doSign(Map<String, Object> objectMap, String sginkey) {
        String result = "";

        try {
            Map<String, String> map = new HashMap();
            objectMap.forEach((key, value) -> {
                map.put(key, value.toString());
            });
            List<Entry<String, String>> infoIds = new ArrayList(map.entrySet());
            Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
                public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                    return ((String)o1.getKey()).toString().compareTo((String)o2.getKey());
                }
            });
            StringBuilder sb = new StringBuilder();
            Iterator var6 = infoIds.iterator();

            while(true) {
                Entry item;
                do {
                    if(!var6.hasNext()) {
                        result = sb.toString();
                        result = sign(result, sginkey, "utf-8");
                        return result;
                    }

                    item = (Entry)var6.next();
                } while(item.getKey() == null && item.getKey() == "");

                String key = (String)item.getKey();
                String val = (String)item.getValue();
                if(val != "" && val != null) {
                    sb.append(key + "=" + val);
                }
            }
        } catch (Exception var10) {
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> orderPayStatusMsg = new HashMap();
            orderPayStatusMsg.put("orderId", "123");
            orderPayStatusMsg.put("payStatus", "1");
            orderPayStatusMsg.put("time", System.currentTimeMillis() + "");
            String sign = doSign(orderPayStatusMsg, "TrafficMallMD5Secret");
            Map<String, Object> backRequest = new HashMap();
            backRequest.put("sign", sign);
            backRequest.put("orderPayStatusMsg", orderPayStatusMsg);
            System.out.println(sign);
            System.out.println(backRequest);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}

