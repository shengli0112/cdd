package com.cdd.gsl.vo;

public class EntrustConditionVo {
    private Long userId;

    private String city;

    private String county;

    private String town;

    //厂房 土地 仓库
    private Integer entrustType;

    //使用类型 求租 求购 出租 出售
    private Integer entrustUseType;

    //面积 开始 类似 100
    private Integer areaFrom;

    //面积 结束 类似 10000
    private Integer areaTo;

    //面积排序 1 默认排序 2 面积从小到大 3 面积从大到小
    private Integer areaOrder;

    //第几页
    private Integer pageNo=1;

    //每页多少条记录
    private Integer pageSize = 10;

    private Integer from;

    private String keyword;

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

    public Integer getEntrustUseType() {
        return entrustUseType;
    }

    public void setEntrustUseType(Integer entrustUseType) {
        this.entrustUseType = entrustUseType;
    }

    public Integer getAreaFrom() {
        return areaFrom;
    }

    public void setAreaFrom(Integer areaFrom) {
        this.areaFrom = areaFrom;
    }

    public Integer getAreaTo() {
        return areaTo;
    }

    public void setAreaTo(Integer areaTo) {
        this.areaTo = areaTo;
    }

    public Integer getAreaOrder() {
        return areaOrder;
    }

    public void setAreaOrder(Integer areaOrder) {
        this.areaOrder = areaOrder;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
