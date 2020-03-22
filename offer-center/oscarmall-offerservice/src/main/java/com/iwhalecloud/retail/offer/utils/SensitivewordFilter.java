package com.iwhalecloud.retail.offer.utils;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @ClassName SensitivewordFilter
 * @Author wangzhongbao
 * @Date 2019/5/8 14:02
 **/
@Component
public class SensitivewordFilter {

    // 最小匹配规则
    public static final int MIN_MATCH_TYPE = 1;

    // 最大匹配规则
    public static final int MAX_MATCH_TYPE = 2;

    @Autowired
    private SensitiveWordInit sensitiveWordInit;

    /**
     * 判断文字是否包含敏感字符
     * 
     * @author chenming
     * @date 2014年4月20日 下午4:28:30
     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public boolean isContaintSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            // 判断是否包含敏感字符
            int matchFlag = this.checkSensitiveWord(txt, i, matchType);
            // 大于0存在，返回true
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     * 
     * @author chenming
     * @date 2014年4月20日 下午5:10:52
     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<>();

        // 由于代码规范原因改为了while循环
        int i = 1;
        while (i < txt.length()) {
            // 判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i, matchType);
            // 存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length;
            }
            else {
                i++;
            }
        }
        return sensitiveWordList;
    }

    public Set<String> getSensitiveWordByTenantId(String txt, int matchType, String tenantId) {
        Set<String> sensitiveWordList = new HashSet<>();

        // 由于代码规范原因改为了while循环
        int i = 1;
        while (i < txt.length()) {
            // 判断是否包含敏感字符
            int length = checkSensitiveWordByTenantId(txt, i, matchType, tenantId);
            // 存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length;
            }
            else {
                i++;
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     * 
     * @author chenming
     * @date 2014年4月20日 下午5:12:07
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    public String replaceSensitiveWordByTenantId(String txt, int matchType, String replaceChar, String tenantId) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> set = getSensitiveWordByTenantId(txt, matchType, tenantId);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     * 
     * @author chenming
     * @date 2014年4月20日 下午5:21:19
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private String getReplaceChars(String replaceChar, int length) {

        StringBuilder bld = new StringBuilder();
        for (int i = 1; i < length; i++) {
            bld.append(replaceChar);
        }
        return bld.toString();
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * 
     * @author chenming
     * @date 2014年4月20日 下午4:31:03
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        // 匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        boolean exists1 = false;
        Map nowMap = sensitiveWordInit.initKeyWord();
        if (nowMap != null) {
            for (int i = beginIndex; i < txt.length(); i++) {
                word = txt.charAt(i);
                // 获取指定key
                nowMap = (Map) nowMap.get(word);
                // 存在，则判断是否为最后一个
                if (nowMap != null) {
                    // 找到相应key，匹配标识+1
                    matchFlag++;
                    // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                    if ("1".equals(nowMap.get("isEnd"))) {
                        // 结束标志位为true
                        flag = true;
                        // 最小规则，直接返回,最大规则还需继续查找
                        if (SensitivewordFilter.MIN_MATCH_TYPE == matchType) {
                            exists1 = true;
                        }
                    }
                }
                // 不存在，直接返回
                else {
                    exists1 = true;
                }
                if (exists1) {
                    break;
                }
            }
        }
        // 长度必须大于等于1，为词
        if (matchFlag < MAX_MATCH_TYPE || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    public int checkSensitiveWordByTenantId(String txt, int beginIndex, int matchType, String tenantId) {
        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        // 匹配标识数默认为0
        int matchFlag = 0;
        boolean exists = false;
        char word = 0;
        Map nowMap = sensitiveWordInit.initKeyWordByTenantId(tenantId);
        if (MapUtils.isNotEmpty(nowMap)) {
            for (int i = beginIndex; i < txt.length(); i++) {
                word = txt.charAt(i);
                // 获取指定key
                nowMap = (Map) nowMap.get(word);
                // 存在，则判断是否为最后一个
                if (nowMap != null) {
                    // 找到相应key，匹配标识+1
                    matchFlag++;
                    // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                    if ("1".equals(nowMap.get("isEnd"))) {
                        // 结束标志位为true
                        flag = true;
                        // 最小规则，直接返回,最大规则还需继续查找
                        if (SensitivewordFilter.MIN_MATCH_TYPE == matchType) {
                            exists = true;
                        }
                    }
                }
                // 不存在，直接返回
                else {
                    exists = true;
                }
                if (exists) {
                    break;
                }
            }
        }
        // 长度必须大于等于1，为词
        if (!flag || matchFlag < MAX_MATCH_TYPE) {
            matchFlag = 0;
        }
        return matchFlag;
    }

}
