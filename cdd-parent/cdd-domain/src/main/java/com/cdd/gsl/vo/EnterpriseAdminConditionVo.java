package com.cdd.gsl.vo;

public class EnterpriseAdminConditionVo {

    private String keyword;
    //第几页
    private Integer page;

    //每页多少条记录
    private Integer limit = 10;

    private Long userId;

    private Integer from;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getFrom() {
        return (page-1) * limit;
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
