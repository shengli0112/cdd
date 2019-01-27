package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.ApplyBrokerInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyBrokerInfoDomainMapper {
    int countByExample(ApplyBrokerInfoDomainExample example);

    int deleteByExample(ApplyBrokerInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ApplyBrokerInfoDomain record);

    int insertSelective(ApplyBrokerInfoDomain record);

    List<ApplyBrokerInfoDomain> selectByExample(ApplyBrokerInfoDomainExample example);

    ApplyBrokerInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ApplyBrokerInfoDomain record, @Param("example") ApplyBrokerInfoDomainExample example);

    int updateByExample(@Param("record") ApplyBrokerInfoDomain record, @Param("example") ApplyBrokerInfoDomainExample example);

    int updateByPrimaryKeySelective(ApplyBrokerInfoDomain record);

    int updateByPrimaryKey(ApplyBrokerInfoDomain record);
}