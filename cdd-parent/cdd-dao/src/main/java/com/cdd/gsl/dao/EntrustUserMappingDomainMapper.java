package com.cdd.gsl.dao;

import com.cdd.gsl.domain.EntrustUserMappingDomain;
import com.cdd.gsl.domain.EntrustUserMappingDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EntrustUserMappingDomainMapper {
    int countByExample(EntrustUserMappingDomainExample example);

    int deleteByExample(EntrustUserMappingDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EntrustUserMappingDomain record);

    int insertSelective(EntrustUserMappingDomain record);

    List<EntrustUserMappingDomain> selectByExample(EntrustUserMappingDomainExample example);

    EntrustUserMappingDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EntrustUserMappingDomain record, @Param("example") EntrustUserMappingDomainExample example);

    int updateByExample(@Param("record") EntrustUserMappingDomain record, @Param("example") EntrustUserMappingDomainExample example);

    int updateByPrimaryKeySelective(EntrustUserMappingDomain record);

    int updateByPrimaryKey(EntrustUserMappingDomain record);
}