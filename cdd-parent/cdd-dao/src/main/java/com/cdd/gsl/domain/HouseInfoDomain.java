package com.cdd.gsl.domain;

import java.util.Date;

public class HouseInfoDomain {
    private Long id;

    private String title;

    private String phone;

    private String city;

    private String country;

    private String street;

    private Integer area;

    private Integer sellingPrice;

    private String electricity;

    private Integer houseType;

    private String houseNumber;

    private Integer houseUseType;

    private String floor;

    private String fireControl;

    private String contacts;

    private Integer status;

    private Date createTs;

    private Date updateTs;

    private String background;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity == null ? null : electricity.trim();
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber == null ? null : houseNumber.trim();
    }

    public Integer getHouseUseType() {
        return houseUseType;
    }

    public void setHouseUseType(Integer houseUseType) {
        this.houseUseType = houseUseType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getFireControl() {
        return fireControl;
    }

    public void setFireControl(String fireControl) {
        this.fireControl = fireControl == null ? null : fireControl.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background == null ? null : background.trim();
    }
}