package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseTypeDomain;
import com.cdd.gsl.domain.HouseTypeDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseTypeDomainMapper {
    int countByExample(HouseTypeDomainExample example);

    int deleteByExample(HouseTypeDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseTypeDomain record);

    int insertSelective(HouseTypeDomain record);

    List<HouseTypeDomain> selectByExample(HouseTypeDomainExample example);

    HouseTypeDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseTypeDomain record, @Param("example") HouseTypeDomainExample example);

    int updateByExample(@Param("record") HouseTypeDomain record, @Param("example") HouseTypeDomainExample example);

    int updateByPrimaryKeySelective(HouseTypeDomain record);

    int updateByPrimaryKey(HouseTypeDomain record);
}