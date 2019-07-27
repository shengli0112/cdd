package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseInfoDomainMapper {
    int countByExample(HouseInfoDomainExample example);

    int deleteByExample(HouseInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseInfoDomain record);

    int insertSelective(HouseInfoDomain record);

    List<HouseInfoDomain> selectByExampleWithBLOBs(HouseInfoDomainExample example);

    List<HouseInfoDomain> selectByExample(HouseInfoDomainExample example);

    HouseInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseInfoDomain record, @Param("example") HouseInfoDomainExample example);

    int updateByExampleWithBLOBs(@Param("record") HouseInfoDomain record, @Param("example") HouseInfoDomainExample example);

    int updateByExample(@Param("record") HouseInfoDomain record, @Param("example") HouseInfoDomainExample example);

    int updateByPrimaryKeySelective(HouseInfoDomain record);

    int updateByPrimaryKeyWithBLOBs(HouseInfoDomain record);

    int updateByPrimaryKey(HouseInfoDomain record);
}