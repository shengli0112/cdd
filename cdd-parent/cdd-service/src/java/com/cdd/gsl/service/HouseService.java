package com.cdd.gsl.service;

import com.cdd.gsl.domain.HouseInfoDomain;

public interface HouseService {
    void addHouse(HouseInfoDomain houseInfoDomain);

    void updateHouse(HouseInfoDomain houseInfoDomain);

    HouseInfoDomain findHouseInfoById(Long houseId);
}
