package com.iwhalecloud.retail.changan.DTO.resp;

/**
 * @author ZhengKai
 * @data 2019-11-11 16:03
 */

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "data",
        "success",
        "msg",
        "msgs"
})
public class AppResponseDTO implements Serializable {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("data")
    private Object data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("msgs")
    private Object msgs;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The code
     */
    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The data
     */
    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    /**
     * @param data The data
     */
    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The msg
     */
    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return The msgs
     */
    @JsonProperty("msgs")
    public Object getMsgs() {
        return msgs;
    }

    /**
     * @param msgs The msgs
     */
    @JsonProperty("msgs")
    public void setMsgs(Object msgs) {
        this.msgs = msgs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(data).append(success).append(msg).append(msgs).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AppResponseDTO) == false) {
            return false;
        }
        AppResponseDTO rhs = ((AppResponseDTO) other);
        return new EqualsBuilder().append(code, rhs.code).append(data, rhs.data).append(success, rhs.success).append(msg, rhs.msg).append(msgs, rhs.msgs).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
