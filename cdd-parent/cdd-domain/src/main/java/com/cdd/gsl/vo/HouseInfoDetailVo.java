package com.cdd.gsl.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class HouseInfoDetailVo {
    private Long id;

    private String title;

    private String city;

    private String county;

    private String street;

    private String houseNumber;

    private Integer area;

    private Integer sellingPrice;

    private String electricity;

    private Integer houseType;

    private Integer houseUseType;

    private Integer floor;

    private Integer fireControl;

    private String contacts;

    private String phone;

    private Integer status;

    private Date createTs;

    private String background;

    private Integer houseStatus;

    private String coverArea;

    private String houseEdge;

    private Long userId;

    private BigDecimal singlePrice;

    private Integer priceType;


    private String useArea;

    private Integer signContract;

    private String town;

    private List<HouseInfoDomainVo> likes;

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
        this.title = title;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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
        this.electricity = electricity;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getHouseUseType() {
        return houseUseType;
    }

    public void setHouseUseType(Integer houseUseType) {
        this.houseUseType = houseUseType;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFireControl() {
        return fireControl;
    }

    public void setFireControl(Integer fireControl) {
        this.fireControl = fireControl;
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


    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getCoverArea() {
        return coverArea;
    }

    public void setCoverArea(String coverArea) {
        this.coverArea = coverArea;
    }

    public String getHouseEdge() {
        return houseEdge;
    }

    public void setHouseEdge(String houseEdge) {
        this.houseEdge = houseEdge;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getUseArea() {
        return useArea;
    }

    public void setUseArea(String useArea) {
        this.useArea = useArea;
    }

    public Integer getSignContract() {
        return signContract;
    }

    public void setSignContract(Integer signContract) {
        this.signContract = signContract;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public List<HouseInfoDomainVo> getLikes() {
        return likes;
    }

    public void setLikes(List<HouseInfoDomainVo> likes) {
        this.likes = likes;
    }
}