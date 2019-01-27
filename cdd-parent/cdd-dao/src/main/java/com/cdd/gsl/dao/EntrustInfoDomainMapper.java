package com.cdd.gsl.dao;

import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.domain.EntrustInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EntrustInfoDomainMapper {
    int countByExample(EntrustInfoDomainExample example);

    int deleteByExample(EntrustInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EntrustInfoDomain record);

    int insertSelective(EntrustInfoDomain record);

    List<EntrustInfoDomain> selectByExample(EntrustInfoDomainExample example);

    EntrustInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EntrustInfoDomain record, @Param("example") EntrustInfoDomainExample example);

    int updateByExample(@Param("record") EntrustInfoDomain record, @Param("example") EntrustInfoDomainExample example);

    int updateByPrimaryKeySelective(EntrustInfoDomain record);

    int updateByPrimaryKey(EntrustInfoDomain record);
}