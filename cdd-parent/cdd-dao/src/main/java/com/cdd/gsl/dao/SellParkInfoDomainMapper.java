package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellParkInfoDomainMapper {
    int countByExample(SellParkInfoDomainExample example);

    int deleteByExample(SellParkInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SellParkInfoDomain record);

    int insertSelective(SellParkInfoDomain record);

    List<SellParkInfoDomain> selectByExample(SellParkInfoDomainExample example);

    SellParkInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SellParkInfoDomain record, @Param("example") SellParkInfoDomainExample example);

    int updateByExample(@Param("record") SellParkInfoDomain record, @Param("example") SellParkInfoDomainExample example);

    int updateByPrimaryKeySelective(SellParkInfoDomain record);

    int updateByPrimaryKey(SellParkInfoDomain record);
}