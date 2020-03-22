package com.iwhalecloud.retail.offer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.common.annotation.DisableTenantParam;
import com.iwhalecloud.retail.offer.dto.req.ContactChannelReq;
import com.iwhalecloud.retail.offer.dto.resp.ContactChannelResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryContactChannelResp;
import com.iwhalecloud.retail.offer.entity.ContactChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxiaofei
 * @date 2019/03/04
 */
@Mapper
public interface ContactChannelMapper extends BaseMapper<ContactChannel> {

    /**
     * 查询渠道
     * 
     * @return List<ContactChannel>
     */
    @DisableTenantParam
    List<ContactChannelResp> listContactChannel();

    ArrayList<ContactChannelResp> queryContactChannelAll();

    /**
     * 根据条件查询渠道
     * 
     * @return List<ContactChannelResp>
     */
    List<ContactChannelResp> qryContactChannelList(@Param("req") ContactChannelReq req);

    /**
     * 根据条件分页查询渠道
     * 
     * @return Page<ContactChannelResp>
     */
    Page<ContactChannelResp> qryContactChannelPage(Page<ContactChannelResp> page, @Param("req") ContactChannelReq req);

    /**
     * 根据id查询渠道
     * 
     * @param contactChannelId String
     * @return ContactChannelResp
     */
    @DisableTenantParam
    QueryContactChannelResp qryContactChannelByContactChannelId(@Param("contactChannelId") String contactChannelId);

    /**
     * 根据商品id查询渠道名称
     * 
     * @param goodsId
     * @return
     */
    List<String> qryContactChannelNameByGoodsId(@Param("goodsId") String goodsId);

    /**
     * 查询渠道名称
     * 
     * @return List<ContactChannel>
     */
    List<ContactChannelResp> listContactChannelName();

}
