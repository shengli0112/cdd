package com.cdd.gsl.admin;

public class EnterpriseAdminConditionVo {

    //第几页
    private Integer page=1;

    //每页多少条记录
    private Integer limit = 10;

    private Integer from;

    private String keyword;

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


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
