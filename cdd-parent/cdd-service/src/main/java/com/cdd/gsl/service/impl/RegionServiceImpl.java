package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.RegionCityInfoDomainMapper;
import com.cdd.gsl.dao.RegionCountyInfoDao;
import com.cdd.gsl.dao.RegionCountyInfoDomainMapper;
import com.cdd.gsl.dao.RegionTownInfoDao;
import com.cdd.gsl.domain.RegionCityInfoDomain;
import com.cdd.gsl.domain.RegionCityInfoDomainExample;
import com.cdd.gsl.service.RegionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionCityInfoDomainMapper regionCityInfoDomainMapper;

    @Autowired
    private RegionCountyInfoDomainMapper regionCountyInfoDomainMapper;

    @Autowired
    private RegionCountyInfoDao regionCountyInfoDao;

    @Autowired
    private RegionTownInfoDao regionTownInfoDao;


    @Override
    public CommonResult<List<String>> findAllCity() {
        CommonResult<List<String>> commonResult = new CommonResult<List<String>>();
        RegionCityInfoDomainExample regionCityInfoDomainExample = new RegionCityInfoDomainExample();
        List<RegionCityInfoDomain> regionCityInfoDomainList = regionCityInfoDomainMapper.selectByExample(regionCityInfoDomainExample);
        List<String> cityList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(regionCityInfoDomainList)){
            regionCityInfoDomainList.forEach(regionCityInfoDomain ->{
                cityList.add(regionCityInfoDomain.getCityName());
            });
        }
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(cityList);
        return commonResult;
    }

    @Override
    public CommonResult<List<String>> findCountyByCity(String city) {
        CommonResult<List<String>> commonResult = new CommonResult<List<String>>();
        List<String> countyList = regionCountyInfoDao.findCountyNameByCityName(city);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(countyList);
        return commonResult;
    }

    @Override
    public CommonResult findTownByCounty(String county) {
        CommonResult<List<String>> commonResult = new CommonResult<List<String>>();
        List<String> countyList = regionTownInfoDao.findTownNameByCountyName(county);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(countyList);
        return commonResult;
    }
}
