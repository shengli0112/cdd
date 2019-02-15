package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ProvinceCityCountyTownVillageMappingDomain;
import com.cdd.gsl.domain.ProvinceCityCountyTownVillageMappingDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProvinceCityCountyTownVillageMappingDomainMapper {
    int countByExample(ProvinceCityCountyTownVillageMappingDomainExample example);

    int deleteByExample(ProvinceCityCountyTownVillageMappingDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProvinceCityCountyTownVillageMappingDomain record);

    int insertSelective(ProvinceCityCountyTownVillageMappingDomain record);

    List<ProvinceCityCountyTownVillageMappingDomain> selectByExample(ProvinceCityCountyTownVillageMappingDomainExample example);

    ProvinceCityCountyTownVillageMappingDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProvinceCityCountyTownVillageMappingDomain record, @Param("example") ProvinceCityCountyTownVillageMappingDomainExample example);

    int updateByExample(@Param("record") ProvinceCityCountyTownVillageMappingDomain record, @Param("example") ProvinceCityCountyTownVillageMappingDomainExample example);

    int updateByPrimaryKeySelective(ProvinceCityCountyTownVillageMappingDomain record);

    int updateByPrimaryKey(ProvinceCityCountyTownVillageMappingDomain record);
}