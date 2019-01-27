package com.cdd.gsl.vo;

public class EntrustConditionVo {
    private Long userId;

    private Integer entrustType;

    //第几页
    private Integer pageNo;

    //每页多少条记录
    private Integer pageSize = 10;

    private Integer from;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(Integer entrustType) {
        this.entrustType = entrustType;
    }

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
