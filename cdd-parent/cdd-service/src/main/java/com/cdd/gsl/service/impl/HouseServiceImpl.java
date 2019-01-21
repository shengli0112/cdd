package com.cdd.gsl.service.impl;

import com.cdd.gsl.dao.HouseInfoDao;
import com.cdd.gsl.dao.HouseInfoDomainMapper;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomainExample;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public HouseInfoDetailVo findHouseInfoById(Long houseId) {
        HouseInfoDetailVo houseInfoDetailVo = houseInfoDao.selectHouseInfoById(houseId);
        List<HouseInfoDomainVo> houseInfoDomainVos = houseInfoDao.selectHouseInfoListByLike();
        houseInfoDetailVo.setLikes(houseInfoDomainVos);
        return houseInfoDetailVo;
    }

    @Override
    public List<HouseInfoDomainVo> findHouseInfoList(HouseConditionVo houseConditionVo) {

        List<HouseInfoDomainVo> houseInfoDomainList = houseInfoDao.selectHouseInfoListByCondition(houseConditionVo);
        return houseInfoDomainList;
    }
}
