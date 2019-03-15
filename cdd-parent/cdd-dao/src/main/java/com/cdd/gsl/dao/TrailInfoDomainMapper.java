package com.cdd.gsl.dao;

import com.cdd.gsl.domain.TrailInfoDomain;
import com.cdd.gsl.domain.TrailInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrailInfoDomainMapper {
    int countByExample(TrailInfoDomainExample example);

    int deleteByExample(TrailInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TrailInfoDomain record);

    int insertSelective(TrailInfoDomain record);

    List<TrailInfoDomain> selectByExample(TrailInfoDomainExample example);

    TrailInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TrailInfoDomain record, @Param("example") TrailInfoDomainExample example);

    int updateByExample(@Param("record") TrailInfoDomain record, @Param("example") TrailInfoDomainExample example);

    int updateByPrimaryKeySelective(TrailInfoDomain record);

    int updateByPrimaryKey(TrailInfoDomain record);
}