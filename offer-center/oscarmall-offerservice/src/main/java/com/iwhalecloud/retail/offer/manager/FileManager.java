package com.iwhalecloud.retail.offer.manager;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.AssertUtil;
import com.iwhalecloud.retail.common.utils.FileInfoUtil;
import com.iwhalecloud.retail.offer.consts.FileTypeDef;
import com.iwhalecloud.retail.offer.consts.OfferBaseMessageCodeEnum;
import com.iwhalecloud.retail.offer.dto.req.FileDeleteReq;
import com.iwhalecloud.retail.offer.dto.req.FileUploadReq;
import com.iwhalecloud.retail.offer.dto.resp.FileUploadResp;
import com.iwhalecloud.retail.offer.entity.ProdGoods;
import com.iwhalecloud.retail.offer.mapper.ProdGoodsMapper;
import com.iwhalecloud.retail.offer.utils.FastdfsTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/7 <br>
 * @see com.iwhalecloud.retail.offer.manager <br>
 * @since V9.0C<br>
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FileManager {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    @Resource
    private ProdGoodsMapper prodGoodsMapper;


    public FileUploadResp uploadFile(MultipartFile file, FileUploadReq request) throws BaseException {
        log.info("FileManager uploadFile start");
        AssertUtil.isNotNull(file, OfferBaseMessageCodeEnum.FILE_IS_NULL);
        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getType(), OfferBaseMessageCodeEnum.PIC_TYPE_IS_NULL);
        AssertUtil.isNotEmpty(showUrl, OfferBaseMessageCodeEnum.SHOW_URL_EMPTY);
        checkUpload(request);
        FileInfoUtil.checkPicType(file.getOriginalFilename());
        StorePath storePath = upload(file);
        saveFileFullPath(request, storePath.getFullPath());
        String token = FastdfsTokenUtil.getToken(storePath.getPath());

        String url = showUrl + storePath.getFullPath();

        if (null != token) {
            url = url + '?' + token;
        }

        FileUploadResp response = new FileUploadResp();
        response.setUrl(url);
        log.info("FileManager uploadFile end");
        return response;

    }

    public FileUploadResp uploadSaleFile(MultipartFile file) throws BaseException {
        log.info("FileManager uploadSaleFile start");

        AssertUtil.isNotNull(file, OfferBaseMessageCodeEnum.FILE_IS_NULL);
        AssertUtil.isNotEmpty(showUrl, OfferBaseMessageCodeEnum.SHOW_URL_EMPTY);

        StorePath storePath = upload(file);
        String token = FastdfsTokenUtil.getToken(storePath.getPath());

        String url = showUrl + storePath.getFullPath();

        if (null != token) {
            url = url + '?' + token;
        }

        FileUploadResp response = new FileUploadResp();
        response.setUrl(url);
        response.setRealUrl(storePath.getFullPath());

        log.info("FileManager uploadSaleFile end");
        return response;
    }


    private void checkUpload(FileUploadReq request) throws BaseException {
        log.info("FileManager checkUpload start");

        if (FileTypeDef.THUMBNAIL.equals(request.getType())) {
            AssertUtil.isNotEmpty(request.getKey(), OfferBaseMessageCodeEnum.KEY_IS_NULL);
            log.info("FileManager checkUpload start");
            return;
        }
        throw new BaseException(CommonBaseMessageCodeEnum.SYSTEM_ERROR);
    }


    private void saveFileFullPath(FileUploadReq request, String fullPath) throws BaseException {
        log.info("FileManager saveFileFullPath start");
        if (FileTypeDef.THUMBNAIL.equals(request.getType())) {
            String goodsId = request.getKey();
            saveGoodsThumbnail(goodsId, fullPath);
        }
        log.info("FileManager saveFileFullPath end");
    }


    private StorePath upload(MultipartFile file) throws BaseException {
        log.info("FileManager upload start");
        StorePath storePath;
        try {
            storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        }
        catch (Exception e) {
            throw new BaseException(OfferBaseMessageCodeEnum.FILE_UPLOAD_FAIL);
        }
        log.info("FileManager upload end");
        return storePath;
    }


    public void saveGoodsThumbnail(String goodsId, String fullPath) throws BaseException {
        log.info("FileManager saveGoodsThumbnail start");
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(goodsId, null);
        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        prodGoods.setThumbnail(fullPath);
        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);
        log.info("FileManager saveGoodsThumbnail end");
    }

    public void deleteFile(FileDeleteReq request) throws BaseException {
        log.info("FileManager deleteFile start");
        AssertUtil.isNotNull(request, OfferBaseMessageCodeEnum.PARAM_ERROR);
        AssertUtil.isNotEmpty(request.getType(), OfferBaseMessageCodeEnum.PIC_TYPE_IS_NULL);
        AssertUtil.isNotEmpty(request.getUrl(), OfferBaseMessageCodeEnum.PIC_URL_IS_NULL);
        checkAndDeleteInDatabase(request);
        log.info("FileManager deleteFile end");
    }

    private void checkAndDeleteInDatabase(FileDeleteReq request) throws BaseException {
        log.info("FileManager checkAndDeleteInDatabase start");

        if (FileTypeDef.THUMBNAIL.equals(request.getType())) {
            AssertUtil.isNotEmpty(request.getKey(), OfferBaseMessageCodeEnum.KEY_IS_NULL);
            deleteGoodsThumbnail(request);
            log.info("FileManager checkAndDeleteInDatabase end");
            return;
        }
        throw new BaseException(OfferBaseMessageCodeEnum.PARAM_ERROR);
    }

    public void deleteGoodsThumbnail(FileDeleteReq request) throws BaseException {
        log.info("FileManager deleteGoodsThumbnail start");
        ProdGoods prodGoods = prodGoodsMapper.selectByGoodsId(request.getKey(), null);
        if (null == prodGoods) {
            throw new BaseException(OfferBaseMessageCodeEnum.THE_GOOD_NOT_EXIST);
        }

        prodGoods.setThumbnail(null);
        prodGoodsMapper.updateProdGoodsWithOutBuyCount(prodGoods);
        log.info("FileManager deleteGoodsThumbnail end");
    }

}
