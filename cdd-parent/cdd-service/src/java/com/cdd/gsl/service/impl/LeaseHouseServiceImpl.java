package com.cdd.gsl.service.impl;

import com.cdd.gsl.dao.LeaseHouseInfoDomainMapper;
import com.cdd.gsl.domain.LeaseHouseInfoDomain;
import com.cdd.gsl.service.LeaseHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseHouseServiceImpl implements LeaseHouseService {

    @Autowired
    private LeaseHouseInfoDomainMapper leaseHouseInfoDomainMapper;

    @Override
    public void addLeaseHouse(LeaseHouseInfoDomain houseInfoDomain) {
        leaseHouseInfoDomainMapper.insertSelective(houseInfoDomain);
    }

    @Override
    public void updateLeaseHouse(LeaseHouseInfoDomain houseInfoDomain) {
        leaseHouseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
    }

    @Override
    public LeaseHouseInfoDomain findLeaseHouseInfoById(Long houseId) {
        LeaseHouseInfoDomain leaseHouseInfoDomain = leaseHouseInfoDomainMapper.selectByPrimaryKey(houseId);
        return leaseHouseInfoDomain;
    }
}
