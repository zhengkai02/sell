package com.iwhalecloud.retail.offer.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

/**
 * <Description> <br>
 *
 * @author huang.anxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/3/6 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface UtilsMapper {

    Date getCurrentDate();
}
