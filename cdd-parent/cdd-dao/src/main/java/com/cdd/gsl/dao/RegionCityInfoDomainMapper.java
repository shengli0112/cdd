package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionCityInfoDomain;
import com.cdd.gsl.domain.RegionCityInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionCityInfoDomainMapper {
    int countByExample(RegionCityInfoDomainExample example);

    int deleteByExample(RegionCityInfoDomainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionCityInfoDomain record);

    int insertSelective(RegionCityInfoDomain record);

    List<RegionCityInfoDomain> selectByExample(RegionCityInfoDomainExample example);

    RegionCityInfoDomain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionCityInfoDomain record, @Param("example") RegionCityInfoDomainExample example);

    int updateByExample(@Param("record") RegionCityInfoDomain record, @Param("example") RegionCityInfoDomainExample example);

    int updateByPrimaryKeySelective(RegionCityInfoDomain record);

    int updateByPrimaryKey(RegionCityInfoDomain record);
}