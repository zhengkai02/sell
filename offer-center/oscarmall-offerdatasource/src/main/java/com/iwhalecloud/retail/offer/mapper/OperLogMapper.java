package com.iwhalecloud.retail.offer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.entity.OperLog;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/4/28 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {

    void batchInsertOperLog(List<OperLog> operLogList);
}
