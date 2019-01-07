package com.cdd.gsl.dao;

import com.cdd.gsl.domain.FollowInfoDomain;
import com.cdd.gsl.domain.FollowInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowInfoDomainMapper {
    int countByExample(FollowInfoDomainExample example);

    int deleteByExample(FollowInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FollowInfoDomain record);

    int insertSelective(FollowInfoDomain record);

    List<FollowInfoDomain> selectByExample(FollowInfoDomainExample example);

    FollowInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FollowInfoDomain record, @Param("example") FollowInfoDomainExample example);

    int updateByExample(@Param("record") FollowInfoDomain record, @Param("example") FollowInfoDomainExample example);

    int updateByPrimaryKeySelective(FollowInfoDomain record);

    int updateByPrimaryKey(FollowInfoDomain record);
}