package com.cdd.gsl.vo;

public class EnterpriseConditionVo {

    private String keyword;
    //第几页
    private Integer pageNo;

    //每页多少条记录
    private Integer pageSize = 10;

    private Long userId;

    private Integer from;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
