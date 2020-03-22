package com.iwhalecloud.retail.changan.service;

import com.iwhalecloud.retail.changan.DTO.req.ProductRequestDTO;
import com.iwhalecloud.retail.changan.VO.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;

/**
 * @author ZhengKai
 * @data 2019-11-07 13:31
 */
public interface ProductService {

    ResultVO<String> syncProduct(String productId);

    ResultVO<String> createProduct(ProductRequestDTO productRequestDTO);

    ResultVO<String> updateProduct(ProductRequestDTO productRequestDTO);

    ResultVO<String> deleteProduct(String productId);
}
