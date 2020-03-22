package com.iwhalecloud.retail.common.controller;

import com.iwhalecloud.retail.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iwhalecloud.retail.common.dto.FileResultResp;
import com.iwhalecloud.retail.common.manager.FastDFSClientManager;

import lombok.extern.slf4j.Slf4j;


/**
 * 文件接口
 *
 * @author fanxiaofei
 * @date 2019-02-25
 */
@Slf4j
@RestController("commonFastDFSClientController")
@RequestMapping("/oscar/fastdfs")
@ConditionalOnProperty(prefix = "fdfs", value = "enabled", havingValue = "true")
public class FastDFSClientController {

    @Autowired
    @Qualifier("commonFastDFSClientManager")
    private FastDFSClientManager fastDFSClientManager;


    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     */
    @PostMapping(value = "/upload/file")
    public FileResultResp uploadFile(MultipartFile file) throws BaseException {
        log.debug("FastDFSClientController uploadFile start");
        FileResultResp resultResp = fastDFSClientManager.uploadFile(file);
        log.debug("FastDFSClientController uploadFile end");
        return resultResp;
    }


    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return FileResultResp
     */
    @PostMapping(value = "/delete/file")
    public FileResultResp deleteFile(String fileUrl) {
        log.debug("FastDFSClientController deleteFile start");
        FileResultResp resultResp = fastDFSClientManager.deleteFile(fileUrl);
        log.debug("FastDFSClientController deleteFile end");
        return resultResp;
    }
}
