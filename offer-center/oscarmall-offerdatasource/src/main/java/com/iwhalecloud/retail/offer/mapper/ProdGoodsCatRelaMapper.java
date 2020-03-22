package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCat;
import com.iwhalecloud.retail.offer.entity.ProdGoodsCatRela;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/30 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0<br>
 */
@Mapper
public interface ProdGoodsCatRelaMapper extends BaseMapper<ProdGoodsCatRela> {

    /**
     * 根据销售目录id 查询关联的管理目录信息
     * @param catId
     * @return
     * @throws BaseException
     */
    @DisableTenantParam
    List<ProdGoodsCat> qrySaleCatgRelaMgnCatg(@Param("catId") String catId) throws BaseException;

    /**
     * 根据管理目录id 查询关联的销售目录信息
     * @param manageCatId String
     */
    @DisableTenantParam
    List<ProdGoodsCatRela> qryManagementCatgRelaByMgnId(@Param("manageCatId") String manageCatId) throws BaseException;

    /**
     * 根据销售目录标识查询关联管理目录关系
     * @param catId
     * @return
     * @throws BaseException
     */
    List<ProdGoodsCatRela> qryProdGoodsRela(@Param("catId") String catId) throws BaseException;

    /**
     * 根据销售目录标识 删除与管理目录的关联关系
     * @param catId
     * @throws BaseException
     */
    void deleteProdGoodsRelaByCatId(@Param("catId") String catId, @Param("modifyBy") String modifyBy) throws BaseException;
}
