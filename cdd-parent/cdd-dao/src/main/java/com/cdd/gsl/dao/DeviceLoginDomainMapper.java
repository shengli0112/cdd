package com.cdd.gsl.dao;

import com.cdd.gsl.domain.DeviceLoginDomain;
import com.cdd.gsl.domain.DeviceLoginDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceLoginDomainMapper {
    int countByExample(DeviceLoginDomainExample example);

    int deleteByExample(DeviceLoginDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceLoginDomain record);

    int insertSelective(DeviceLoginDomain record);

    List<DeviceLoginDomain> selectByExample(DeviceLoginDomainExample example);

    DeviceLoginDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceLoginDomain record, @Param("example") DeviceLoginDomainExample example);

    int updateByExample(@Param("record") DeviceLoginDomain record, @Param("example") DeviceLoginDomainExample example);

    int updateByPrimaryKeySelective(DeviceLoginDomain record);

    int updateByPrimaryKey(DeviceLoginDomain record);
}