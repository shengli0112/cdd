package com.cdd.gsl.vo;

public class ConsumeRecordConditionVo {
    private Long userId;

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer from;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
