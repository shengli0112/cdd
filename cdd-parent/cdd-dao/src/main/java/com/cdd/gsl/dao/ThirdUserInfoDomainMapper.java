package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ThirdUserInfoDomain;
import com.cdd.gsl.domain.ThirdUserInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThirdUserInfoDomainMapper {
    int countByExample(ThirdUserInfoDomainExample example);

    int deleteByExample(ThirdUserInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ThirdUserInfoDomain record);

    int insertSelective(ThirdUserInfoDomain record);

    List<ThirdUserInfoDomain> selectByExample(ThirdUserInfoDomainExample example);

    ThirdUserInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ThirdUserInfoDomain record, @Param("example") ThirdUserInfoDomainExample example);

    int updateByExample(@Param("record") ThirdUserInfoDomain record, @Param("example") ThirdUserInfoDomainExample example);

    int updateByPrimaryKeySelective(ThirdUserInfoDomain record);

    int updateByPrimaryKey(ThirdUserInfoDomain record);
}