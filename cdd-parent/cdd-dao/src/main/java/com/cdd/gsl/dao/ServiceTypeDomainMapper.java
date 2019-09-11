package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ServiceTypeDomain;
import com.cdd.gsl.domain.ServiceTypeDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceTypeDomainMapper {
    int countByExample(ServiceTypeDomainExample example);

    int deleteByExample(ServiceTypeDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ServiceTypeDomain record);

    int insertSelective(ServiceTypeDomain record);

    List<ServiceTypeDomain> selectByExample(ServiceTypeDomainExample example);

    ServiceTypeDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ServiceTypeDomain record, @Param("example") ServiceTypeDomainExample example);

    int updateByExample(@Param("record") ServiceTypeDomain record, @Param("example") ServiceTypeDomainExample example);

    int updateByPrimaryKeySelective(ServiceTypeDomain record);

    int updateByPrimaryKey(ServiceTypeDomain record);
}