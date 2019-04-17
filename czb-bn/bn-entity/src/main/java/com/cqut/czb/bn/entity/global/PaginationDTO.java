package com.cqut.czb.bn.entity.global;

import javax.validation.constraints.Min;

public class PaginationDTO {

    /**
     * 当前页
     * */
    @Min(value = 1, message = "当前页最小为1")
    private int pageNum;

    /**
     * 每页数据条数
     * */
    @Min(value = 1, message = "每页数据条数最小为1")
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
