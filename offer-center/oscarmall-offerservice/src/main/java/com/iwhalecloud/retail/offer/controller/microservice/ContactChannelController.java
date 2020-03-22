package com.iwhalecloud.retail.offer.controller.microservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.iwhalecloud.retail.common.dto.ResultVO;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.utils.ResultVOCheckUtil;
import com.iwhalecloud.retail.offer.dto.req.ContactChannelReq;
import com.iwhalecloud.retail.offer.dto.req.QueryContactChannelReq;
import com.iwhalecloud.retail.offer.dto.resp.ContactChannelResp;
import com.iwhalecloud.retail.offer.dto.resp.QueryContactChannelResp;
import com.iwhalecloud.retail.offer.entity.ChannelType;
import com.iwhalecloud.retail.offer.entity.ContactChannel;
import com.iwhalecloud.retail.offer.manager.ContactChannelManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "渠道")
@RestController("contactChannelMicroService")
@RequestMapping("/offer/contactchannel")
@Slf4j
public class ContactChannelController {

    @Autowired
    private ContactChannelManager contactChannelManager;

    /**
     * 查询渠道
     *
     * @return List<ContactChannel>
     * @author fanxiaofei
     */
    @ApiOperation(value = "查询渠道")
    @GetMapping("/list")
    public List<ContactChannelResp> listContactChannel() {
        log.info("CommonController listContactChannel start");
        List<ContactChannelResp> result = contactChannelManager.listContactChannel();
        log.info("CommonController listContactChannel end");
        return result;
    }

    /**
     * 查询渠道类型（域表）
     *
     * @return List<ContactChannel>
     * @author huminghang
     */
    @ApiOperation(value = "查询渠道类型（域表）")
    @GetMapping("/channeltype/list")
    public List<ChannelType> qryChannelTypeList() {
        log.info("ContactChannelController qryChannelTypeList start");
        List<ChannelType> result = contactChannelManager.qryChannelTypeList();
        log.info("ContactChannelController qryChannelTypeList end");
        return result;
    }

    /**
     * 根据条件查询渠道
     *
     * @return List<ContactChannelResp>
     * @author huminghang
     */
    @ApiOperation(value = "根据条件查询渠道")
    @PostMapping("/channellist")
    public List<ContactChannelResp> qryContactChannelList(@RequestBody ContactChannelReq req) {
        log.info("ContactChannelController qryContactChannelList start");
        List<ContactChannelResp> result = contactChannelManager.qryContactChannelList(req);
        log.info("ContactChannelController qryContactChannelList end");
        return result;
    }

    /**
     * 根据条件分页查询渠道
     *
     * @return Page<ContactChannelResp>
     * @author ji.maowei
     */
    @ApiOperation(value = "根据条件分页查询渠道")
    @PostMapping("/channelpage")
    public Page<ContactChannelResp> qryContactChannelPage(@RequestBody ContactChannelReq req) {
        log.info("ContactChannelController qryContactChannelPage start");
        Page<ContactChannelResp> result = contactChannelManager.qryContactChannelPage(req);
        log.info("ContactChannelController qryContactChannelPaget end");
        return result;
    }

    @PostMapping("/detail/{contactChannelId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContactChannel qryContactChannel(@ApiParam(value = "渠道ID") @PathVariable String contactChannelId) {
        log.info("ContactChannelController qryContactChannel start");
        ContactChannel response = contactChannelManager.qryContactChannel(contactChannelId);
        log.info("ContactChannelController qryContactChannel end");
        return response;
    }

    @PostMapping("/add")
    public void addContactChannel(@RequestBody ContactChannel req) throws BaseException {
        log.info("ContactChannelController addContactChannel start");
        contactChannelManager.addContactChannel(req);
        log.info("ContactChannelController addContactChannel end");
    }

    @PostMapping("/mod")
    public void modContactChannel(@RequestBody ContactChannel req) throws BaseException {
        log.info("ContactChannelController modContactChannel start");
        contactChannelManager.modContactChannel(req);
        log.info("ContactChannelController addContactChannel end");
    }

    @ApiOperation(value = "根据渠道id查询渠道")
    @PostMapping("/qryByChannelId")
    public ResultVO<QueryContactChannelResp> qryByChannelId(@RequestBody QueryContactChannelReq req) {
        log.info("ContactChannelController qryByChannelId start");
        ResultVO<QueryContactChannelResp> result = ResultVOCheckUtil
            .buildResultVO(contactChannelManager::qryByChannelId, req);
        log.info("ContactChannelController qryByChannelId end");
        return result;
    }

    @ApiOperation(value = "查询所有的接触渠道，分库微服务调用")
    @PostMapping("/all/list")
    public ResultVO<ArrayList<ContactChannelResp>> queryContactChannelAll() {
        log.info("ContactChannelController queryContactChannelAll start");
        ResultVO<ArrayList<ContactChannelResp>> result = ResultVOCheckUtil
            .buildResultVO(contactChannelManager::queryContactChannelAll);
        log.info("ContactChannelController queryContactChannelAll end");
        return result;
    }
}
