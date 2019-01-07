package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LandInfoDomain;
import com.cdd.gsl.domain.LandInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LandInfoDomainMapper {
    int countByExample(LandInfoDomainExample example);

    int deleteByExample(LandInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LandInfoDomain record);

    int insertSelective(LandInfoDomain record);

    List<LandInfoDomain> selectByExample(LandInfoDomainExample example);

    LandInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LandInfoDomain record, @Param("example") LandInfoDomainExample example);

    int updateByExample(@Param("record") LandInfoDomain record, @Param("example") LandInfoDomainExample example);

    int updateByPrimaryKeySelective(LandInfoDomain record);

    int updateByPrimaryKey(LandInfoDomain record);
}