package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.PinyinUtils;
import com.cdd.gsl.common.util.RedisUtil;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.RegionCityInfoDomain;
import com.cdd.gsl.domain.RegionCityInfoDomainExample;
import com.cdd.gsl.service.RegionService;
import com.cdd.gsl.vo.CityVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Autowired
    private RegionCityInfoDao regionCityInfoDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SearchCityUserDao searchCityUserDao;

    private Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);


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

    @Override
    public CommonResult findFirstCodeCity(Long userId) {
        CommonResult commonResult = new CommonResult();
//        String str = redisUtil.hget("city","40");
//        System.out.println(str);
        List<String> cityNameList = regionCityInfoDao.selectCityName();
        List<CityVo> cityVos = new ArrayList<>();
        cityNameList.forEach(name ->{
            CityVo cityVo = new CityVo();
            cityVo.setCityName(name);
            cityVos.add(cityVo);
        });
        Collections.sort(cityVos, new Comparator<CityVo>() {

            public int compare(CityVo o1, CityVo o2) {

                CityVo newo1 = new CityVo();

                newo1.setFirstCode(PinyinUtils.ToPinyinAndGetFirstChar(o1.getCityName()).substring(0, 1));

                CityVo newo2 = new CityVo();

                newo2.setFirstCode(PinyinUtils.ToPinyinAndGetFirstChar(o2.getCityName()).substring(0, 1));

                int i = newo1.getFirstCode().compareTo(newo2.getFirstCode());

                return i;

            }

        });

        Set<String> firstCodeSet = new HashSet<>();
        cityVos.forEach(cityVo -> {
            cityVo.setFirstCode(PinyinUtils.ToPinyinAndGetFirstChar(cityVo.getCityName()).substring(0, 1));
            firstCodeSet.add(cityVo.getFirstCode());
        });
        List<CityVo> cityVoList = new ArrayList<>();
        for(String code:firstCodeSet){
            StringBuffer sb = new StringBuffer();
            CityVo result = new CityVo();
            for(CityVo cityVo:cityVos){
                if(cityVo.getFirstCode().equals(code)){
                    sb.append(cityVo.getCityName()+",");
                }
            }
            result.setFirstCode(code);
            result.setCityName(sb.toString().substring(0,sb.toString().length()-1));
            cityVoList.add(result);
        }
        /*JSONObject data = new JSONObject();
        if(userId != null){
            List<String> cityList = searchCityUserDao.selectCityByUserId(userId);
            data.put("rememberCity",cityList);
        }
        data.put("city",cityVoList);*/
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(cityVoList);
        return commonResult;
    }

    @Override
    public CommonResult findCityName(String city) {
        CommonResult commonResult = new CommonResult();
        try{
            if(!StringUtils.isEmpty(city)){
                List<String> cityNameList = regionCityInfoDao.selectCityByCityName(city);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(cityNameList);
            }
        }catch (Exception e){
            logger.error("findCityName error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }

        return commonResult;
    }
}
