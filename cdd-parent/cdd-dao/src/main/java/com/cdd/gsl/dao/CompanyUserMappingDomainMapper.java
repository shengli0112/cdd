package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CompanyUserMappingDomain;
import com.cdd.gsl.domain.CompanyUserMappingDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyUserMappingDomainMapper {
    int countByExample(CompanyUserMappingDomainExample example);

    int deleteByExample(CompanyUserMappingDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CompanyUserMappingDomain record);

    int insertSelective(CompanyUserMappingDomain record);

    List<CompanyUserMappingDomain> selectByExample(CompanyUserMappingDomainExample example);

    CompanyUserMappingDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CompanyUserMappingDomain record, @Param("example") CompanyUserMappingDomainExample example);

    int updateByExample(@Param("record") CompanyUserMappingDomain record, @Param("example") CompanyUserMappingDomainExample example);

    int updateByPrimaryKeySelective(CompanyUserMappingDomain record);

    int updateByPrimaryKey(CompanyUserMappingDomain record);
}