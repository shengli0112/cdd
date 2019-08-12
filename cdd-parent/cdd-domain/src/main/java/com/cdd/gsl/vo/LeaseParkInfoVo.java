package com.cdd.gsl.vo;

import java.util.Date;
import java.util.List;

public class LeaseParkInfoVo {
    private Long id;

    private String parkName;

    private String tag;

    private String totalArea;

    private String address;

    private String priceType;

    private String city;

    private String county;

    private Integer unitPrice;

    private String industry;

    private String contacts;

    private String phone;


    private String description;


    private Long userId;

    private String town;

    private String background;

    private Integer status;

    private List<LeaseParkInfoVo> likes;

    private Integer top;

    private String parkType;

    private SingleUserInfoVo user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
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

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LeaseParkInfoVo> getLikes() {
        return likes;
    }

    public void setLikes(List<LeaseParkInfoVo> likes) {
        this.likes = likes;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public SingleUserInfoVo getUser() {
        return user;
    }

    public void setUser(SingleUserInfoVo user) {
        this.user = user;
    }
}
