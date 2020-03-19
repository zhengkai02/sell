package indi.zk.mall.product.controller;

import indi.zk.mall.product.DO.ProductCategory;
import indi.zk.mall.product.DO.ProductInfo;
import indi.zk.mall.product.DTO.CartDTO;
import indi.zk.mall.product.VO.ProductInfoVO;
import indi.zk.mall.product.VO.ProductVO;
import indi.zk.mall.product.VO.ResultVO;
import indi.zk.mall.product.common.DecreaseStockInput;
import indi.zk.mall.product.common.ProductInfoOutput;
import indi.zk.mall.product.service.CategoryService;
import indi.zk.mall.product.service.ProductService;
import indi.zk.mall.product.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhengKai
 * @data 2019-09-15 12:15
 */
@RestController
@RequestMapping(value = "/product", produces = "application/json")
@Api(value = "商品中心控制器")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Value("${eid.list}")
    private String eids;

    /**
     * 1. 查询所有在架商品
     * 2. 获取类目Type列表
     * 3. 查询类目
     * 4. 构造函数
     *
     * @return
     */
    @ApiOperation(value = "查询商品", notes = "查询所有在架商品")
    @GetMapping("/list")
    public ResultVO<ProductVO> listProduct(HttpServletRequest request) {


        List<String> eidList = Arrays.asList(eids.split(","));
        if (eidList.contains("789")) {
            log.info("true");
        }
        log.info("ProductController eidList=[{}]", eidList);

        List<ProductInfo> productInfoList = productService.findUpAll();

        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表（订单服务专用）
     *
     * @param productIdList
     * @return
     */
    @ApiOperation(value = "获取商品列表", notes = "根据商品ID列表获取商品列表(订单服务专用)")
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {
        return productService.findProductsByIdList(productIdList);
    }

    @ApiOperation(value = "扣减库存", notes = "根据购物车内容扣减库存")
    @PostMapping("/decreaseStock")
    public void dereaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStack(decreaseStockInputList);
    }
}
