package com.iwhalecloud.retail.offer.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 上传文件后的数据返回对象，便于前台获取数据.
 *
 * @author fanxiaofei
 * @date 2019-02-25
 */
@Data
public class FileResultResp {

    /**
     * 返回状态编码
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "返回状态编码")
    private String code;

    /**
     * 返回信息
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "返回信息")
    private String message;

    /**
     * 文件路径
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    /**
     * 文件实际路径
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "文件实际路径")
    private String realFilePath;

    /**
     * 文件名称
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件类型
     */
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "文件类型")
    private String fileType;

}
