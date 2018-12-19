package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LeaseHouseInfoDomain;
import com.cdd.gsl.domain.LeaseHouseInfoDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LeaseHouseInfoDomainMapper {
    int countByExample(LeaseHouseInfoDomainExample example);

    int deleteByExample(LeaseHouseInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LeaseHouseInfoDomain record);

    int insertSelective(LeaseHouseInfoDomain record);

    List<LeaseHouseInfoDomain> selectByExample(LeaseHouseInfoDomainExample example);

    LeaseHouseInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LeaseHouseInfoDomain record, @Param("example") LeaseHouseInfoDomainExample example);

    int updateByExample(@Param("record") LeaseHouseInfoDomain record, @Param("example") LeaseHouseInfoDomainExample example);

    int updateByPrimaryKeySelective(LeaseHouseInfoDomain record);

    int updateByPrimaryKey(LeaseHouseInfoDomain record);
}