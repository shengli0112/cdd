package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.domain.ServiceInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceInfoDomainMapper {
    int countByExample(ServiceInfoDomainExample example);

    int deleteByExample(ServiceInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ServiceInfoDomain record);

    int insertSelective(ServiceInfoDomain record);

    List<ServiceInfoDomain> selectByExampleWithBLOBs(ServiceInfoDomainExample example);

    List<ServiceInfoDomain> selectByExample(ServiceInfoDomainExample example);

    ServiceInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ServiceInfoDomain record, @Param("example") ServiceInfoDomainExample example);

    int updateByExampleWithBLOBs(@Param("record") ServiceInfoDomain record, @Param("example") ServiceInfoDomainExample example);

    int updateByExample(@Param("record") ServiceInfoDomain record, @Param("example") ServiceInfoDomainExample example);

    int updateByPrimaryKeySelective(ServiceInfoDomain record);

    int updateByPrimaryKeyWithBLOBs(ServiceInfoDomain record);

    int updateByPrimaryKey(ServiceInfoDomain record);
}