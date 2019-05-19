package com.cdd.gsl.vo;

public class SingleUserBrokerVo {
    private Long userId;

    private String phone;

    private String username;

    //头像
    private String portrait;

    private Integer applyType;

    private Long applyBrokerId;

    private String serviceArea;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getApplyBrokerId() {
        return applyBrokerId;
    }

    public void setApplyBrokerId(Long applyBrokerId) {
        this.applyBrokerId = applyBrokerId;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }
}
