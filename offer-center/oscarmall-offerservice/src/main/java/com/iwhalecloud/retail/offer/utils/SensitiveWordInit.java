package com.iwhalecloud.retail.offer.utils;

import com.iwhalecloud.retail.offer.entity.SensitiveWords;
import com.iwhalecloud.retail.offer.mapper.SensitiveWordsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @ClassName SensitiveWordInit
 * @Author wangzhongbao
 * @Date 2019/5/8 14:03
 **/
@Component
@Slf4j
public class SensitiveWordInit {

    @Resource
    private SensitiveWordsMapper sensitiveWordsMapper;

    @SuppressWarnings("rawtypes")
    public Map sensitiveWordMap;

    /**
     * @author chenming
     * @date 2014年4月20日 下午2:28:32
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord() {
        try {
            sensitiveWordMap = null;
            // 读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        }
        catch (Exception e) {
            log.info("error SensitiveWordInit :" + e.getMessage());
        }
        return sensitiveWordMap;
    }

    public Map initKeyWordByTenantId(String tenantId) {
        try {
            // 读取敏感词库
            Set<String> keyWordSet = readSensityWordFileByTenantId(tenantId);
            if (null != keyWordSet) {
                // 将敏感词库加入到HashMap中
                addSensitiveWordToHashMap(keyWordSet);
            }
        }
        catch (Exception e) {
            log.info("error SensitiveWordInit :" + e.getMessage());
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 1 } } } } 五 = { isEnd = 0 星 = { isEnd =
     * 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
     * 
     * @author
     * @date
     * @param keyWordSet 敏感词库
     * @version 1.0
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size()); // 初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        // 迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            // 关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 获取
                Object wordMap = nowMap.get(keyChar);

                // 如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                }
                else {
                    // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    // 不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * @Author wangzhongbao
     * @Date 15:15 2019/5/8
     * @Param []
     * @return java.util.Set<java.lang.String>
     **/
    @Scheduled(cron = "0 */5 * * * ?")
    private Set<String> readSensitiveWordFile() {
        List<SensitiveWords> sensitiveWordsList = sensitiveWordsMapper.qryAllSensWords();
        Set<String> set = null;

        if (CollectionUtils.isNotEmpty(sensitiveWordsList)) {
            set = new HashSet<>();
            for (SensitiveWords sensitiveWords : sensitiveWordsList) {
                if (StringUtils.isNotEmpty(sensitiveWords.getWords())) {
                    set.add(sensitiveWords.getWords());
                }
            }
        }

        return set;
    }

    private Set<String> readSensityWordFileByTenantId(String tenantId) {
        List<SensitiveWords> sensitiveWordsList = sensitiveWordsMapper.qryAllSensWordByTenantId(tenantId);
        Set<String> set = null;

        if (CollectionUtils.isNotEmpty(sensitiveWordsList)) {
            set = new HashSet<>();
            for (SensitiveWords sensitiveWords : sensitiveWordsList) {
                if (StringUtils.isNotEmpty(sensitiveWords.getWords())) {
                    set.add(sensitiveWords.getWords());
                }
            }
        }

        return set;
    }
}
