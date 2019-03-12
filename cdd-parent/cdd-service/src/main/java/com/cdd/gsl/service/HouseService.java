package com.cdd.gsl.service;

import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;

import java.util.List;

public interface HouseService {
    void addHouse(HouseInfoDomain houseInfoDomain);

    void updateHouse(HouseInfoDomain houseInfoDomain);

    HouseInfoDetailVo findHouseInfoById(Long houseId);

    List<HouseInfoDomainVo> findHouseInfoList(HouseConditionVo houseConditionVo);

    List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    List<HouseInfoDomainVo> selectCompanyHouseInfoListByCondition(HouseConditionVo houseConditionVo);
}
