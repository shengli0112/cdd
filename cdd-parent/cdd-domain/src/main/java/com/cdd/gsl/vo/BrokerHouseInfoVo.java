package com.cdd.gsl.vo;

import java.util.List;

public class BrokerHouseInfoVo {
    private Long userId;

    private String username;

    private String phone;

    private String portrait;

    //厂房数量
    private int plantCount;

    //仓库数量
    private int storageCount;

    //土地数量
    private int landCount;

    private List<HouseInfoDomainVo> houseList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(int plantCount) {
        this.plantCount = plantCount;
    }

    public int getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(int storageCount) {
        this.storageCount = storageCount;
    }

    public int getLandCount() {
        return landCount;
    }

    public void setLandCount(int landCount) {
        this.landCount = landCount;
    }

    public List<HouseInfoDomainVo> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseInfoDomainVo> houseList) {
        this.houseList = houseList;
    }
}
