package com.iwhalecloud.retail.offer.controller.microservice;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.FileDeleteReq;
import com.iwhalecloud.retail.offer.dto.req.FileUploadReq;
import com.iwhalecloud.retail.offer.dto.resp.FileUploadResp;
import com.iwhalecloud.retail.offer.manager.FileManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/7 <br>
 * @see com.iwhalecloud.retail.offer.controller <br>
 * @since V9.0C<br>
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/offer/file")
@Slf4j
public class FileController {

    public static final String FC_START = "FileController uploadFile start";
    public static final String FC_END = "FileController uploadFile end";

    @Autowired
    private FileManager fileManager;

    @PostMapping("/upload")
    public FileUploadResp uploadFile(MultipartFile file, FileUploadReq request) throws BaseException {
        log.info(FC_START);
        FileUploadResp fileUploadResp = fileManager.uploadFile(file, request);
        log.info(FC_END);
        return fileUploadResp;
    }

    @PostMapping("/delete")
    public void deleteFile(FileDeleteReq request) throws BaseException {
        log.info(FC_START);
        fileManager.deleteFile(request);
        log.info(FC_END);
    }

    @PostMapping("/sale/upload")
    public FileUploadResp uploadSaleFile(@Param("file") MultipartFile file) throws BaseException {
        log.info(FC_START);
        FileUploadResp fileUploadResp = fileManager.uploadSaleFile(file);
        log.info(FC_END);
        return fileUploadResp;
    }

    @PostMapping("/save/goodsthumbnail")
    public ResultVO saveGoodsThumbnail(@ApiParam(value = "商品ID") @RequestParam("goodsId") String goodsId, @RequestParam("fullPath") String fullPath) {
        log.info(FC_START);
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setCode(CommonBaseMessageCodeEnum.SUCCESS.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SUCCESS.getMessage());
            fileManager.saveGoodsThumbnail(goodsId, fullPath);
        }
        catch (BaseException e) {
            log.error("BaseException = [{}]", e);
            resultVO.setCode(e.getCode());
            resultVO.setMessage(e.getDesc());
        }
        catch (Exception e) {
            log.error("Exception = [{}]", e);
            resultVO.setCode(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus());
            resultVO.setMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage());
        }
        log.info(FC_END);
        return resultVO;
    }

    @PostMapping("/del/goodsthumbnail")
    public ResultVO deleteGoodsThumbnail(@RequestBody FileDeleteReq request) throws BaseException {
        log.info(FC_START);
        ResultVO resultVO = ResultVOCheckUtil.buildResultVONoReturn(fileManager::deleteGoodsThumbnail, request);
        log.info(FC_END);
        return resultVO;
    }
}
