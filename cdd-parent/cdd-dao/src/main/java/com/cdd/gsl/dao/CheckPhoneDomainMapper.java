package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CheckPhoneDomain;
import com.cdd.gsl.domain.CheckPhoneDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckPhoneDomainMapper {
    int countByExample(CheckPhoneDomainExample example);

    int deleteByExample(CheckPhoneDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CheckPhoneDomain record);

    int insertSelective(CheckPhoneDomain record);

    List<CheckPhoneDomain> selectByExample(CheckPhoneDomainExample example);

    CheckPhoneDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CheckPhoneDomain record, @Param("example") CheckPhoneDomainExample example);

    int updateByExample(@Param("record") CheckPhoneDomain record, @Param("example") CheckPhoneDomainExample example);

    int updateByPrimaryKeySelective(CheckPhoneDomain record);

    int updateByPrimaryKey(CheckPhoneDomain record);
}