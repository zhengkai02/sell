package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.resp.FileResultResp;
import com.iwhalecloud.retail.offer.manager.FastDFSClientManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件接口
 *
 * @author fanxiaofei
 * @date 2019-02-25
 */
@Api(tags = "文件接口")
@Slf4j
@Controller
@RequestMapping("/offer/fastdfs")
public class FastDFSClientController {

    @Autowired
    private FastDFSClientManager fastDFSClientManager;


    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     */
    @ResponseBody
    @PostMapping(value = "/upload/file")
    public FileResultResp uploadFile(MultipartFile file) throws BaseException {
        log.info("FastDFSClientController uploadFile start");
        FileResultResp resultResp = fastDFSClientManager.uploadFile(file);
        log.info("FastDFSClientController uploadFile end");
        return resultResp;
    }


    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return FileResultResp
     */
    @ResponseBody
    @PostMapping(value = "/delete/file")
    public FileResultResp deleteFile(String fileUrl) {
        log.info("FastDFSClientController deleteFile start");
        FileResultResp resultResp = fastDFSClientManager.deleteFile(fileUrl);
        log.info("FastDFSClientController deleteFile end");
        return resultResp;
    }

}
