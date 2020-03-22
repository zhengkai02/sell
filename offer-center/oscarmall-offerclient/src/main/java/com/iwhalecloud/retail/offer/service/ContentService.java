package com.iwhalecloud.retail.offer.service;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.dto.client.req.UpdateContentBaseReq;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseResponseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.TagDTO;
import com.iwhalecloud.retail.offer.dto.req.ContentAddReq;
import com.iwhalecloud.retail.offer.dto.req.ContentBaseQryReq;
import com.iwhalecloud.retail.offer.dto.req.QryTagReq;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FeignClient(name = "${oscar.rest.content.name}", path = "${oscar.rest.content.path}")
public interface ContentService {

    @PostMapping("/contentBase/queryContentDeatil")
    ContentBaseResponseDTO queryContentDeatil(@RequestParam(value = "contentId") Long contentId);

    /**
     * 更新内容的赞和踩
     * @param req UpdateContentBaseReq
     * @return ResultVO
     */
    @PostMapping(value = "/contentBase/updateUsefulOrUseless")
    ResultVO updateUsefulOrUseless(@RequestBody UpdateContentBaseReq req);

    /**
     * 新增商品内容
     * @param req ContentAddReq
     * @return ContentBaseDTO
     */
    @PostMapping("/contentBase/addContentBase")
    ContentBaseDTO addContentBase(@RequestBody ContentAddReq req) throws BaseException;

    /**
     * 更新评论量
     * @param contentId
     */
    @PutMapping("/contentBase/evaluationCount")
    void updateEvaluationCount(@RequestParam(value = "contentId") Long contentId, @RequestParam(value = "modifyBy") String modifyBy);

    @PostMapping("/contentBase/qryList")
    ResultVO<ArrayList<ContentBaseDTO>> selectContentBaseListByCond(@RequestBody ContentBaseQryReq req) throws BaseException;

    @PostMapping("/contentTag/tagList")
    ResultVO<ArrayList<TagDTO>> qryTagListByCond(@RequestBody QryTagReq req);

    @PostMapping("/queryMaterialAndText/{contentId}")
    List<Map<String,String>> queryMaterialAndText(@RequestParam(value = "contentId") Long contentId) throws BaseException;



}
