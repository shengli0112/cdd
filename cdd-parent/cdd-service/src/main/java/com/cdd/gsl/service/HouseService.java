package com.cdd.gsl.service;

import com.cdd.gsl.domain.HouseInfoDomain;

import java.util.List;

public interface HouseService {
    void addHouse(HouseInfoDomain houseInfoDomain);

    void updateHouse(HouseInfoDomain houseInfoDomain);

    HouseInfoDomain findHouseInfoById(Long houseId);

    List<HouseInfoDomain> findHouseInfoList(Integer houseType,Long houseId,Integer pageSize);
}
