package com.iwhalecloud.retail.offer.manager;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.resp.FileResultResp;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;



/**
 * 文件接口
 *
 * @author fanxiaofei
 * @date 2019-02-25
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        log.info("FastDFSClientManager uploadFile start");

        FileResultResp result = new FileResultResp();

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')
            + 1, file.getOriginalFilename().length());
        if (!allowUpload.contains(suffix.trim().toLowerCase())) {
            throw new BaseException(CommonBaseMessageCodeEnum.PIC_TYPE_ERROE);
        }

        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String token = FastdfsTokenUtil.getToken(storePath.getPath());
            String url = showUrl + storePath.getFullPath();
            if (null != token) {
                url = url + '?' + token;
            }
            result.setFilePath(storePath.getFullPath());
            result.setRealFilePath(url);
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

        log.info("FastDFSClientManager uploadFile end");
        return result;
    }


    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return FileResultResp
     */
    public FileResultResp deleteFile(String fileUrl) {
        log.info("FastDFSClientManager deleteFile start");

        FileResultResp result = new FileResultResp();
        result.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
        result.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
        if (StringUtils.isEmpty(fileUrl)) {
            result.setCode(OfferBaseMessageCodeEnum.FILE_IS_NULL.getStatus());
            result.setMessage(OfferBaseMessageCodeEnum.FILE_IS_NULL.getMessage());
            return result;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        }
        catch (Exception e) {
            log.error("deleteFile Exception = [{}]", e);
            result.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            result.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }

        log.info("FastDFSClientManager deleteFile end");
        return result;
    }

}
