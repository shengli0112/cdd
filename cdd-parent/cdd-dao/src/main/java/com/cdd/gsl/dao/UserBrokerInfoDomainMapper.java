package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserBrokerInfoDomain;
import com.cdd.gsl.domain.UserBrokerInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBrokerInfoDomainMapper {
    int countByExample(UserBrokerInfoDomainExample example);

    int deleteByExample(UserBrokerInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBrokerInfoDomain record);

    int insertSelective(UserBrokerInfoDomain record);

    List<UserBrokerInfoDomain> selectByExample(UserBrokerInfoDomainExample example);

    UserBrokerInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBrokerInfoDomain record, @Param("example") UserBrokerInfoDomainExample example);

    int updateByExample(@Param("record") UserBrokerInfoDomain record, @Param("example") UserBrokerInfoDomainExample example);

    int updateByPrimaryKeySelective(UserBrokerInfoDomain record);

    int updateByPrimaryKey(UserBrokerInfoDomain record);
}