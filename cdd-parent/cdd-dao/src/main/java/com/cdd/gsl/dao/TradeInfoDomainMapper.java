package com.cdd.gsl.dao;

import com.cdd.gsl.domain.TradeInfoDomain;
import com.cdd.gsl.domain.TradeInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeInfoDomainMapper {
    int countByExample(TradeInfoDomainExample example);

    int deleteByExample(TradeInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TradeInfoDomain record);

    int insertSelective(TradeInfoDomain record);

    List<TradeInfoDomain> selectByExample(TradeInfoDomainExample example);

    TradeInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TradeInfoDomain record, @Param("example") TradeInfoDomainExample example);

    int updateByExample(@Param("record") TradeInfoDomain record, @Param("example") TradeInfoDomainExample example);

    int updateByPrimaryKeySelective(TradeInfoDomain record);

    int updateByPrimaryKey(TradeInfoDomain record);
}