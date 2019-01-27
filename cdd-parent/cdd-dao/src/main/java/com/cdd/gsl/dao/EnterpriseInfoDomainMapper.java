package com.cdd.gsl.dao;

import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.domain.EnterpriseInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseInfoDomainMapper {
    int countByExample(EnterpriseInfoDomainExample example);

    int deleteByExample(EnterpriseInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EnterpriseInfoDomain record);

    int insertSelective(EnterpriseInfoDomain record);

    List<EnterpriseInfoDomain> selectByExample(EnterpriseInfoDomainExample example);

    EnterpriseInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EnterpriseInfoDomain record, @Param("example") EnterpriseInfoDomainExample example);

    int updateByExample(@Param("record") EnterpriseInfoDomain record, @Param("example") EnterpriseInfoDomainExample example);

    int updateByPrimaryKeySelective(EnterpriseInfoDomain record);

    int updateByPrimaryKey(EnterpriseInfoDomain record);
}