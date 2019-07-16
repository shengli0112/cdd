package com.cdd.gsl.service;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.InformHouseRecordDomain;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;

import java.util.List;

public interface HouseService {
    CommonResult addHouse(HouseInfoDomain houseInfoDomain);

    CommonResult topHouse(Long houseId,Long userId);

    CommonResult updateHouse(HouseInfoDomain houseInfoDomain);

    void deleteHouse(HouseInfoDomain houseInfoDomain);

    HouseInfoDetailVo findHouseInfoById(Long houseId);

    JSONObject findHouseInfoList(HouseConditionVo houseConditionVo);

    List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    List<HouseInfoDomainVo> selectCompanyHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    CommonResult informHouseInfo(InformHouseRecordDomain informHouseRecordDomain);

    void delayTopHouse();
}
