package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionVillageInfoDomain;
import com.cdd.gsl.domain.RegionVillageInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionVillageInfoDomainMapper {
    int countByExample(RegionVillageInfoDomainExample example);

    int deleteByExample(RegionVillageInfoDomainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionVillageInfoDomain record);

    int insertSelective(RegionVillageInfoDomain record);

    List<RegionVillageInfoDomain> selectByExample(RegionVillageInfoDomainExample example);

    RegionVillageInfoDomain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionVillageInfoDomain record, @Param("example") RegionVillageInfoDomainExample example);

    int updateByExample(@Param("record") RegionVillageInfoDomain record, @Param("example") RegionVillageInfoDomainExample example);

    int updateByPrimaryKeySelective(RegionVillageInfoDomain record);

    int updateByPrimaryKey(RegionVillageInfoDomain record);
}