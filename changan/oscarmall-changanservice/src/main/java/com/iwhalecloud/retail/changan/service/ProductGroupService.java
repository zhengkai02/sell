package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.DTO.ProductGroupDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;


/**
 * @author ZhengKai
 * @data 2019-11-07 13:32
 */
public interface ProductGroupService {

    ResultVO<String> syncProductGroup(String groupId);

    ResultVO<String> createProductGroup(ProductGroupDTO productGroupDTO);

    ResultVO<String> updateProductGroup(ProductGroupDTO productGroupDTO);

    ResultVO<String> deleteProductGroup(String productGroupId);
}
