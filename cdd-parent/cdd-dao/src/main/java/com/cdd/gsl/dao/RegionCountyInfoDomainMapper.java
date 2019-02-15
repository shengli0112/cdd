package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RegionCountyInfoDomain;
import com.cdd.gsl.domain.RegionCountyInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionCountyInfoDomainMapper {
    int countByExample(RegionCountyInfoDomainExample example);

    int deleteByExample(RegionCountyInfoDomainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionCountyInfoDomain record);

    int insertSelective(RegionCountyInfoDomain record);

    List<RegionCountyInfoDomain> selectByExample(RegionCountyInfoDomainExample example);

    RegionCountyInfoDomain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionCountyInfoDomain record, @Param("example") RegionCountyInfoDomainExample example);

    int updateByExample(@Param("record") RegionCountyInfoDomain record, @Param("example") RegionCountyInfoDomainExample example);

    int updateByPrimaryKeySelective(RegionCountyInfoDomain record);

    int updateByPrimaryKey(RegionCountyInfoDomain record);
}