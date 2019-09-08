package com.cdd.gsl.vo;

public class StatisticsInfoVo {
    private String username;

    //新增房源数
    private Integer addHouseCount = 0;

    //新增客源数
    private Integer addCustomerCount = 0;

    //跟进房源数
    private Integer trailHouseCount = 0;

    //跟进客源数
    private Integer trailCustomerCount = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAddHouseCount() {
        return addHouseCount;
    }

    public void setAddHouseCount(Integer addHouseCount) {
        this.addHouseCount = addHouseCount;
    }

    public Integer getAddCustomerCount() {
        return addCustomerCount;
    }

    public void setAddCustomerCount(Integer addCustomerCount) {
        this.addCustomerCount = addCustomerCount;
    }

    public Integer getTrailHouseCount() {
        return trailHouseCount;
    }

    public void setTrailHouseCount(Integer trailHouseCount) {
        this.trailHouseCount = trailHouseCount;
    }

    public Integer getTrailCustomerCount() {
        return trailCustomerCount;
    }

    public void setTrailCustomerCount(Integer trailCustomerCount) {
        this.trailCustomerCount = trailCustomerCount;
    }
}
