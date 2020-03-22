package indi.zk.mall.product.service;

import indi.zk.mall.product.DO.ProductInfo;
import indi.zk.mall.product.common.DecreaseStockInput;

import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:55
 */
public interface ProductService {

    /**
     * 查询所有在架商品列表
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     *
     * @param productIdList
     * @return
     */
    List<ProductInfo> findProductsByIdList(List<String> productIdList);

    /**
     * 扣库存
     *
     * @param carDTOList
     */
    void decreaseStack(List<DecreaseStockInput> carDTOList);
}
