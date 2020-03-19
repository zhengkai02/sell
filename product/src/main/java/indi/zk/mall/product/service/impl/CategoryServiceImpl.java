package indi.zk.mall.product.service.impl;

import indi.zk.mall.product.DO.ProductCategory;
import indi.zk.mall.product.repository.ProductCategoryRepository;
import indi.zk.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhengKai
 * @data 2019-09-15 12:02
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return productCategoryRepository.findByCategoryTypeIn(categoryType);
    }
}
