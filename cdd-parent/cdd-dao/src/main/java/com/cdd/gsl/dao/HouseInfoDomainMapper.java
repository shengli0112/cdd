package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseInfoDomainMapper {
    int countByExample(HouseInfoDomainExample example);

    int deleteByExample(HouseInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseInfoDomain record);

    int insertSelective(HouseInfoDomain record);

    List<HouseInfoDomain> selectByExample(HouseInfoDomainExample example);

    HouseInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseInfoDomain record, @Param("example") HouseInfoDomainExample example);

    int updateByExample(@Param("record") HouseInfoDomain record, @Param("example") HouseInfoDomainExample example);

    int updateByPrimaryKeySelective(HouseInfoDomain record);

    int updateByPrimaryKey(HouseInfoDomain record);
}