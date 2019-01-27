package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ClockCardDomain;
import com.cdd.gsl.domain.ClockCardDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClockCardDomainMapper {
    int countByExample(ClockCardDomainExample example);

    int deleteByExample(ClockCardDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ClockCardDomain record);

    int insertSelective(ClockCardDomain record);

    List<ClockCardDomain> selectByExample(ClockCardDomainExample example);

    ClockCardDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ClockCardDomain record, @Param("example") ClockCardDomainExample example);

    int updateByExample(@Param("record") ClockCardDomain record, @Param("example") ClockCardDomainExample example);

    int updateByPrimaryKeySelective(ClockCardDomain record);

    int updateByPrimaryKey(ClockCardDomain record);
}