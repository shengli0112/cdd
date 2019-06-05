package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserCurrencyMappingDomain;
import com.cdd.gsl.domain.UserCurrencyMappingDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCurrencyMappingDomainMapper {
    int countByExample(UserCurrencyMappingDomainExample example);

    int deleteByExample(UserCurrencyMappingDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserCurrencyMappingDomain record);

    int insertSelective(UserCurrencyMappingDomain record);

    List<UserCurrencyMappingDomain> selectByExample(UserCurrencyMappingDomainExample example);

    UserCurrencyMappingDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserCurrencyMappingDomain record, @Param("example") UserCurrencyMappingDomainExample example);

    int updateByExample(@Param("record") UserCurrencyMappingDomain record, @Param("example") UserCurrencyMappingDomainExample example);

    int updateByPrimaryKeySelective(UserCurrencyMappingDomain record);

    int updateByPrimaryKey(UserCurrencyMappingDomain record);
}