package com.iwhalecloud.retail.common.utils;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.exception.BaseException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FileInfoUtil {

    private FileInfoUtil() {
    }

    private static final int HV_LENGTH = 2;

    public static void checkPicType(String fileName) throws BaseException {
        // 设置允许上传文件类型
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";

        String str = fileName;
        // 是否包含0x00
        int ignoreLen = fileName.indexOf("0x00");
        if (ignoreLen >= 0) {
            str = str.substring(0, ignoreLen);
        }
        // 获取文件后缀
        String suffix = str.substring(str.lastIndexOf('.') + 1, str.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return;
        }

        throw new BaseException(CommonBaseMessageCodeEnum.PIC_TYPE_ERROE);

    }

    /***
     * @Author wangzhongbao
     * @Description
     * @Date 16:10 2019/7/29
     * @Param [file]
     * @return void
     **/
    public static void checkSecurity(MultipartFile file) throws BaseException {
        try {
            Image img = ImageIO.read(file.getInputStream());
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                throw new BaseException(CommonBaseMessageCodeEnum.PIC_TYPE_ERROE);
            }

            byte[] byteArray = IOUtils.toByteArray(file.getInputStream());
            String str = bytesToHexString(byteArray);
            // 匹配16进制中的 <% ( ) %>
            // 匹配16进制中的 <? ( ) ?>
            // 匹配16进制中的 <script | /script> 大小写亦可
            // 通过匹配十六进制代码检测是否存在木马脚本
            String pattern = "(3c25.*?28.*?29.*?253e)|(3c3f.*?28.*?29.*?3f3e)|(3C534352495054)|(2F5343524950543E)|(3C736372697074)|(2F7363726970743E)";
            Pattern mPattern = Pattern.compile(pattern);
            Matcher mMatcher = mPattern.matcher(str);

            if (mMatcher.find()) {
                // 过滤java关键字(java import String print write( read() php request alert
                // system)（暂时先这样解决，这样改动最小，以后想后更好的解决方案再优化）
                String keywordPattern = "(6a617661)|(696d706f7274)|(537472696e67)|(7072696e74)|(777269746528)|(726561642829)|(706870)|(72657175657374)|(616c657274)|(73797374656d)";
                Pattern keywordmPattern = Pattern.compile(keywordPattern);
                Matcher keywordmMatcher = keywordmPattern.matcher(str);
                if (keywordmMatcher.find()) {
                    throw new BaseException(CommonBaseMessageCodeEnum.PIC_TYPE_ERROE);
                }
            }
        }
        catch (IOException ex) {
            throw new BaseException();
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < HV_LENGTH) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
