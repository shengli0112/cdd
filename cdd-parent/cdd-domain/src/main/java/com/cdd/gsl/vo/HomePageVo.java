package com.cdd.gsl.vo;


import java.util.List;

public class HomePageVo {
    private int houseCount;

    private int clientCount;

    //厂房/仓库推荐
    private List<HouseInfoDomainVo> houseInfoDomainVos;

    //产业园区推荐
    private List<ParkInfoVo> parkInfoVos;

    //企业圈
    private List<EnterpriseInfoVo> enterpriseInfoDomains;

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public List<HouseInfoDomainVo> getHouseInfoDomainVos() {
        return houseInfoDomainVos;
    }

    public void setHouseInfoDomainVos(List<HouseInfoDomainVo> houseInfoDomainVos) {
        this.houseInfoDomainVos = houseInfoDomainVos;
    }

    public List<ParkInfoVo> getParkInfoVos() {
        return parkInfoVos;
    }

    public void setParkInfoVos(List<ParkInfoVo> parkInfoVos) {
        this.parkInfoVos = parkInfoVos;
    }

    public List<EnterpriseInfoVo> getEnterpriseInfoDomains() {
        return enterpriseInfoDomains;
    }

    public void setEnterpriseInfoDomains(List<EnterpriseInfoVo> enterpriseInfoDomains) {
        this.enterpriseInfoDomains = enterpriseInfoDomains;
    }
}
