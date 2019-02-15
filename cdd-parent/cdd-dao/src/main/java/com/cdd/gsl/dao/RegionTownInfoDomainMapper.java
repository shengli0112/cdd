package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionTownInfoDomain;
import com.cdd.gsl.domain.RegionTownInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionTownInfoDomainMapper {
    int countByExample(RegionTownInfoDomainExample example);

    int deleteByExample(RegionTownInfoDomainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionTownInfoDomain record);

    int insertSelective(RegionTownInfoDomain record);

    List<RegionTownInfoDomain> selectByExample(RegionTownInfoDomainExample example);

    RegionTownInfoDomain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionTownInfoDomain record, @Param("example") RegionTownInfoDomainExample example);

    int updateByExample(@Param("record") RegionTownInfoDomain record, @Param("example") RegionTownInfoDomainExample example);

    int updateByPrimaryKeySelective(RegionTownInfoDomain record);

    int updateByPrimaryKey(RegionTownInfoDomain record);
}