package com.cdd.gsl.vo;

public class StorageConditionVo {

    //第几页
    private Integer pageNo;

    //每页多少条记录
    private Integer pageSize = 10;

    private Integer from;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getFrom() {
        return (pageNo-1) * pageSize;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
