package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.offer.dto.client.req.QueryGoodsPriceReq;
import com.iwhalecloud.retail.offer.entity.GoodsPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author huminghang<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2019/6/8 <br>
 * @see com.iwhalecloud.retail.offer.mapper <br>
 * @since V9.0C<br>
 */
@Mapper
public interface GoodsPriceMapper extends BaseMapper<GoodsPrice> {

    List<GoodsPrice> qryGoodsPriceList(QueryGoodsPriceReq request);

}
