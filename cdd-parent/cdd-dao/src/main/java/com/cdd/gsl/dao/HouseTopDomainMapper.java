package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseTopDomain;
import com.cdd.gsl.domain.HouseTopDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseTopDomainMapper {
    int countByExample(HouseTopDomainExample example);

    int deleteByExample(HouseTopDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseTopDomain record);

    int insertSelective(HouseTopDomain record);

    List<HouseTopDomain> selectByExample(HouseTopDomainExample example);

    HouseTopDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseTopDomain record, @Param("example") HouseTopDomainExample example);

    int updateByExample(@Param("record") HouseTopDomain record, @Param("example") HouseTopDomainExample example);

    int updateByPrimaryKeySelective(HouseTopDomain record);

    int updateByPrimaryKey(HouseTopDomain record);
}