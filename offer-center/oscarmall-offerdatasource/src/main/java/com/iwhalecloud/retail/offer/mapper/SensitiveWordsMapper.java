package com.iwhalecloud.retail.offer.mapper;

import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.offer.dto.req.QrySensitiveWordsReq;
import com.iwhalecloud.retail.offer.entity.SensitiveWords;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author dong.shaoqiang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/5/6 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0<br>
 */
@Mapper
public interface SensitiveWordsMapper extends BaseMapper<SensitiveWords> {

    /**
     * 查询所有的敏感词
     * @return
     */
    List<SensitiveWords> qryAllSensWords();

    @DisableTenantParam
    List<SensitiveWords> qryAllSensWordByTenantId(@Param("tenantId") String tenantId);

    /**
     * 查询所有的敏感词
     * @return
     */
    Page<SensitiveWords> qryAllSensitiveWords(Page<SensitiveWords> page, @Param("request") QrySensitiveWordsReq request);

    /**
     * 查询符合状态的敏感词名称
     * @param state String
     * @return List<String>
     */
    List<String> listSensitiveWordsByState(@Param("state") String state);
}
