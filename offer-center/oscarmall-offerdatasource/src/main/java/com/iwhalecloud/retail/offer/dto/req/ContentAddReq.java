package com.iwhalecloud.retail.offer.dto.req;

import com.iwhalecloud.retail.offer.dto.client.resp.ContentBaseDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentTagDTO;
import com.iwhalecloud.retail.offer.dto.client.resp.ContentTextDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 内容
 * @author fanxiaofei
 * @date 2019-06-05
 */
@Data
public class ContentAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容基础信息")
    private ContentBaseDTO contentBase;

    @ApiModelProperty(value = "内容标签")
    private List<ContentTagDTO> contentTagList;

    @ApiModelProperty(value = "软文")
    private List<ContentTextDTO> contentTextList;

    @ApiModelProperty(value = "素材信息")
    private ArrayList<Map<String, Object>> itemInfo;

}
