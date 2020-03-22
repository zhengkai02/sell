package indi.zk.mall.product.service;

import indi.zk.mall.product.DO.ProductCategory;

import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 11:57
 */
public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
