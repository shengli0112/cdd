package com.cdd.gsl.vo;

public class EntrustInfoVo {
    private Long entrustId;

    private String username;

    private Integer type;

    //委托类型
    private String entrustType;
    //委托使用类型
    private String entrustUseType;

    private String address;

    private String area;

    //头像
    private String portrait;

    //联系人
    private String contacts;

    private String phone;

    private String business;

    private String createTs;

    //是否查看手机号
    private Integer checkPhone;

    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntrustUseType() {
        return entrustUseType;
    }

    public void setEntrustUseType(String entrustUseType) {
        this.entrustUseType = entrustUseType;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        if(type == 3){
            area = area+"亩";
        }else{
            area = area +"㎡";
        }
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCheckPhone() {
        return checkPhone;
    }

    public void setCheckPhone(Integer checkPhone) {
        this.checkPhone = checkPhone;
    }
}
