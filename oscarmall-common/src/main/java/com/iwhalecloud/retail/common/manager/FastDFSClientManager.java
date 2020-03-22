package com.iwhalecloud.retail.common.manager;

import com.iwhalecloud.retail.common.utils.FileInfoUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.FileResultResp;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 文件接口
 *
 * @author fanxiaofei
 * @date 2019-02-25
 */
@Slf4j
@Component("commonFastDFSClientManager")
@ConditionalOnProperty(prefix = "fdfs", value = "enabled", havingValue = "true")
public class FastDFSClientManager {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUpload;

    /**
     * 上传文件
     * @param file 文件对象
     * @return FileResultResp
     */
    public FileResultResp uploadFile(MultipartFile file) throws BaseException {
        log.debug("FastDFSClientManager uploadFile start");

        FileInfoUtil.checkPicType(file.getOriginalFilename());

        FileResultResp result = new FileResultResp();

        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String token = FastdfsTokenUtil.getToken(storePath.getPath());
            String url = showUrl + storePath.getFullPath();
            if (null != token) {
                url = url + '?' + token;
            }
            result.setRealFilePath(url);
            result.setFilePath(storePath.getFullPath());
            result.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            result.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
        }
        catch (BaseException e) {
            log.error("uploadFile BaseException = [{}]", e);
            result.setCode(e.getCode());
            result.setMessage(e.getDesc());
        }
        catch (Exception e) {
            log.error("uploadFile Exception = [{}]", e);
            result.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            result.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }

        log.debug("FastDFSClientManager uploadFile end");
        return result;
    }


    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return FileResultResp
     */
    public FileResultResp deleteFile(String fileUrl) {
        log.debug("FastDFSClientManager deleteFile start");

        FileResultResp result = new FileResultResp();
        result.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
        result.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
        if (StringUtils.isEmpty(fileUrl)) {
            result.setCode(CommonBaseMessageCodeEnum.FILE_IS_NULL.getStatus());
            result.setMessage(CommonBaseMessageCodeEnum.FILE_IS_NULL.getMessage());
            return result;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        }
        catch (FdfsUnsupportStorePathException e) {
            result.setMessage(e.getMessage());
            result.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            result.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
            log.info("deleteFile Exception: " + e.getMessage());
        }

        log.debug("FastDFSClientManager deleteFile end");
        return result;
    }

}
