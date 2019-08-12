package com.cdd.gsl.vo;

import java.util.Date;
import java.util.List;

public class SellParkInfoVo {
    private Long id;

    private String city;

    private String county;

    private String address;

    private String parkName;

    private Integer totalArea;

    private Integer totalPrice;

    private String contacts;

    private String phone;

    private String tag;

    private String description;

    private Integer status;

    private String createTs;

    private String updateTs;

    private Long userId;

    private String town;

    private String background;

    private String industry;

    private List<SellParkInfoVo> likes;

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Integer totalArea) {
        this.totalArea = totalArea;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<SellParkInfoVo> getLikes() {
        return likes;
    }

    public void setLikes(List<SellParkInfoVo> likes) {
        this.likes = likes;
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
