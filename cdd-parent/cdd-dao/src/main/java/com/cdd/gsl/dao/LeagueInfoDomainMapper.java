package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LeagueInfoDomain;
import com.cdd.gsl.domain.LeagueInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeagueInfoDomainMapper {
    int countByExample(LeagueInfoDomainExample example);

    int deleteByExample(LeagueInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LeagueInfoDomain record);

    int insertSelective(LeagueInfoDomain record);

    List<LeagueInfoDomain> selectByExample(LeagueInfoDomainExample example);

    LeagueInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LeagueInfoDomain record, @Param("example") LeagueInfoDomainExample example);

    int updateByExample(@Param("record") LeagueInfoDomain record, @Param("example") LeagueInfoDomainExample example);

    int updateByPrimaryKeySelective(LeagueInfoDomain record);

    int updateByPrimaryKey(LeagueInfoDomain record);
}