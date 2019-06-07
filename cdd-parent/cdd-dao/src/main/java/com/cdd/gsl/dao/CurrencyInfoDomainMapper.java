package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CurrencyInfoDomain;
import com.cdd.gsl.domain.CurrencyInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurrencyInfoDomainMapper {
    int countByExample(CurrencyInfoDomainExample example);

    int deleteByExample(CurrencyInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CurrencyInfoDomain record);

    int insertSelective(CurrencyInfoDomain record);

    List<CurrencyInfoDomain> selectByExample(CurrencyInfoDomainExample example);

    CurrencyInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CurrencyInfoDomain record, @Param("example") CurrencyInfoDomainExample example);

    int updateByExample(@Param("record") CurrencyInfoDomain record, @Param("example") CurrencyInfoDomainExample example);

    int updateByPrimaryKeySelective(CurrencyInfoDomain record);

    int updateByPrimaryKey(CurrencyInfoDomain record);
}