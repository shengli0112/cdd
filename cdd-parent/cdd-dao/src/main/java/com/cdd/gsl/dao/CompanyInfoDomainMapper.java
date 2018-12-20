package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CompanyInfoDomain;
import com.cdd.gsl.domain.CompanyInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyInfoDomainMapper {
    int countByExample(CompanyInfoDomainExample example);

    int deleteByExample(CompanyInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CompanyInfoDomain record);

    int insertSelective(CompanyInfoDomain record);

    List<CompanyInfoDomain> selectByExample(CompanyInfoDomainExample example);

    CompanyInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CompanyInfoDomain record, @Param("example") CompanyInfoDomainExample example);

    int updateByExample(@Param("record") CompanyInfoDomain record, @Param("example") CompanyInfoDomainExample example);

    int updateByPrimaryKeySelective(CompanyInfoDomain record);

    int updateByPrimaryKey(CompanyInfoDomain record);
}