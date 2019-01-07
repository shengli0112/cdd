package com.cdd.gsl.dao;

import com.cdd.gsl.domain.PlantInfoDomain;
import com.cdd.gsl.domain.PlantInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlantInfoDomainMapper {
    int countByExample(PlantInfoDomainExample example);

    int deleteByExample(PlantInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlantInfoDomain record);

    int insertSelective(PlantInfoDomain record);

    List<PlantInfoDomain> selectByExample(PlantInfoDomainExample example);

    PlantInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlantInfoDomain record, @Param("example") PlantInfoDomainExample example);

    int updateByExample(@Param("record") PlantInfoDomain record, @Param("example") PlantInfoDomainExample example);

    int updateByPrimaryKeySelective(PlantInfoDomain record);

    int updateByPrimaryKey(PlantInfoDomain record);
}