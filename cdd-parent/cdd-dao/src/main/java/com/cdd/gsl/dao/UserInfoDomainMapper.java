package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.domain.UserInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDomainMapper {
    int countByExample(UserInfoDomainExample example);

    int deleteByExample(UserInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserInfoDomain record);

    int insertSelective(UserInfoDomain record);

    List<UserInfoDomain> selectByExample(UserInfoDomainExample example);

    UserInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserInfoDomain record, @Param("example") UserInfoDomainExample example);

    int updateByExample(@Param("record") UserInfoDomain record, @Param("example") UserInfoDomainExample example);

    int updateByPrimaryKeySelective(UserInfoDomain record);

    int updateByPrimaryKey(UserInfoDomain record);
}