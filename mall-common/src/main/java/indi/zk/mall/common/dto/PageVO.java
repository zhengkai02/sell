package com.iwhalecloud.retail.common.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页基础对象.
 *
 * @author Ji.kai
 * @date 2018/10/27 15:27
 */
public class PageVO implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(PageVO.class);

    private static final long serialVersionUID = -6550827640887727324L;

    private static final Integer PAGE_SIZE = 10;

    private Integer pageNo = 1;

    private Integer pageSize = PAGE_SIZE;

    private String sortName;

    private String sortOrder;

    public void setSortName(String sortName) {
        if (sortName != null) {
            if (sortName.contains(";")) {
                log.warn("排序字段中含有;号，可能是sql注入， sortName={}, 过滤掉这个排序内容", sortName);
            }
            else {
                this.sortName = sortName;
            }
        }
    }

    public String getSortName() {
        if (this.sortName == null) {
            return null;
        }
        else if (this.sortName.contains(";")) {
            log.warn("排序字段中含有;号，可能是sql注入， sortName={}, 过滤掉这个排序内容", this.sortName);
            return null;
        }
        else {
            return this.sortName;
        }
    }

    public void setSortOrder(String sortOrder) {
        if (sortOrder != null) {
            if (!"desc".equalsIgnoreCase(sortOrder) && !"asc".equalsIgnoreCase(sortOrder)) {
                log.warn("排序方向字段值不正确， sortOrder={}, 忽略排序方向", sortOrder);
            }
            else {
                this.sortOrder = sortOrder;
            }
        }
    }

    public String getSortOrder() {
        if (this.sortOrder == null) {
            return null;
        }
        else if (!"desc".equalsIgnoreCase(this.sortOrder) && !"asc".equalsIgnoreCase(this.sortOrder)) {
            log.warn("排序方向字段值不正确， sortOrder={}, 忽略排序方向", this.sortOrder);
            return null;
        }
        else {
            return this.sortOrder;
        }
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "PageVO(pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + ", sortName="
            + this.getSortName() + ", sortOrder=" + this.getSortOrder() + ")";
    }
}
