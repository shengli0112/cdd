package com.cdd.gsl.service;

import com.cdd.gsl.domain.LeaseHouseInfoDomain;

public interface LeaseHouseService {
    void addLeaseHouse(LeaseHouseInfoDomain houseInfoDomain);

    void updateLeaseHouse(LeaseHouseInfoDomain houseInfoDomain);

    LeaseHouseInfoDomain findLeaseHouseInfoById(Long houseId);
}
