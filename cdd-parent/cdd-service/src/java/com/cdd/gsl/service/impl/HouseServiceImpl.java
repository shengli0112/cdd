package com.cdd.gsl.service.impl;

import com.cdd.gsl.dao.HouseInfoDao;
import com.cdd.gsl.dao.HouseInfoDomainMapper;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService{
    @Autowired
    private HouseInfoDomainMapper houseInfoDomainMapper;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Override
    public void addHouse(HouseInfoDomain houseInfoDomain) {
        houseInfoDomainMapper.insertSelective(houseInfoDomain);
    }

    @Override
    public void updateHouse(HouseInfoDomain houseInfoDomain) {
        houseInfoDomainMapper.updateByPrimaryKeySelective(houseInfoDomain);
    }

    @Override
    public HouseInfoDomain findHouseInfoById(Long houseId) {
        return houseInfoDao.selectHouseInfoById(houseId);
    }
}
