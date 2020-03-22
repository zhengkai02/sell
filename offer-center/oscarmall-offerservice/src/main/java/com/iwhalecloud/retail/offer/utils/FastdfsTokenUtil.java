package com.iwhalecloud.retail.offer.utils;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/7 <br>
 * @see com.iwhalecloud.retail.common.utils <br>
 * @since V9.0C<br>
 */
@Slf4j
@Component
public final class FastdfsTokenUtil {

    private FastdfsTokenUtil() {
    }

    private static String tokenFlag;

    private static String secretKey;

    private static String url;

    @Value("${fdfs.showUrl}")
    public void setUrl(String showUrl) {
        url = showUrl;
    }

    @Value("${fdfs.http_anti_steal_token}")
    public void setTokenFlag(String temTokenFlag) {
        tokenFlag = temTokenFlag;
    }

    @Value("${fdfs.http_secret_key}")
    public void setSecretKey(String temSecretKey) {
        secretKey = temSecretKey;
    }

    public static String getToken(String fileName) throws BaseException {
        if (!"true".equals(tokenFlag)) {
            return null;
        }

        // unix seconds
        int ts = (int) Instant.now().getEpochSecond();
        String token;
        try {
            token = getToken(fileName, ts, secretKey);
        }
        catch (Exception e) {
            throw new BaseException(OfferBaseMessageCodeEnum.GENERATE_TOKEN_FAIL);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("token=").append(token);
        sb.append("&ts=").append(ts);

        return sb.toString();
    }

    private static String getToken(String fileId, int ts, String secretKey) throws NoSuchAlgorithmException {
        byte[] bsKey = secretKey.getBytes();
        byte[] bsTimestamp = Integer.toString(ts).getBytes();
        byte[] bsFileId = fileId.getBytes();
        byte[] buff = new byte[bsFileId.length + bsKey.length + bsTimestamp.length];
        System.arraycopy(bsFileId, 0, buff, 0, bsFileId.length);
        System.arraycopy(bsKey, 0, buff, bsFileId.length, bsKey.length);
        System.arraycopy(bsTimestamp, 0, buff, bsFileId.length + bsKey.length, bsTimestamp.length);

        return md5(buff);

    }

    public static final int CHAR_A = 32;

    public static final int I_MAX = 16;

    public static final int TMP_NAME = 4;

    private static String md5(byte[] source) throws NoSuchAlgorithmException {
        char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(source);
        int k = 0;
        byte[] tmp = md.digest();
        char[] str = new char[CHAR_A];
        for (int i = 0; i < I_MAX; i++) {
            str[(k++)] = hexDigits[(tmp[i] >>> TMP_NAME & 0xF)];
            str[(k++)] = hexDigits[(tmp[i] & 0xF)];
        }
        return new String(str);
    }

    public static final int GROUP_FOR_SRC = 2;

    public static String repairContent(String content) throws BaseException {
        String patternStr = "<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String result = content;
        List<String> countRepeatStr = new ArrayList<>();
        while (matcher.find()) {
            String src = matcher.group(GROUP_FOR_SRC);
            if (countRepeatStr.contains(src)) {
                continue;
            }
            countRepeatStr.add(src);
            String replaceSrc;
            if (src.startsWith("http://") || src.startsWith("https://")) {
                replaceSrc = src.split("\\?")[0].split("//")[1];
                replaceSrc = replaceSrc.substring(replaceSrc.indexOf('/') + 1);
            }
            else {
                String token = getToken(src.substring(src.indexOf('/') + 1));
                if (StringUtils.isEmpty(token)) {
                    replaceSrc = url + src;
                }
                else {
                    replaceSrc = url + src + "?" + token;
                }
            }
            result = result.replace(src, replaceSrc);
        }
        return result;
    }

    /**
     * 封装showUrl
     * 
     * @param thumbnail String
     * @return String
     */
    public static String buildShowUrl(String thumbnail) throws BaseException {
        log.info("FastdfsTokenUtil buildShowUrl start, thumbnail = [{}]", thumbnail);

        String showUrl = null;
        if (StringUtils.isNotEmpty(thumbnail)) {
            String substring = thumbnail.substring(thumbnail.indexOf('/') + 1);
            String token = getToken(substring);
            if (StringUtils.isEmpty(token)) {
                showUrl = url + thumbnail;
            }
            else {
                showUrl = url + thumbnail + "?" + token;
            }
        }

        log.info("FastdfsTokenUtil buildShowUrl end, showUrl = [{}]", showUrl);
        return showUrl;
    }

}
