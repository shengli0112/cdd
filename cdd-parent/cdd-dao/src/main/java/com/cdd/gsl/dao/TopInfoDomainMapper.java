package com.cdd.gsl.dao;

import com.cdd.gsl.domain.TopInfoDomain;
import com.cdd.gsl.domain.TopInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopInfoDomainMapper {
    int countByExample(TopInfoDomainExample example);

    int deleteByExample(TopInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TopInfoDomain record);

    int insertSelective(TopInfoDomain record);

    List<TopInfoDomain> selectByExample(TopInfoDomainExample example);

    TopInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TopInfoDomain record, @Param("example") TopInfoDomainExample example);

    int updateByExample(@Param("record") TopInfoDomain record, @Param("example") TopInfoDomainExample example);

    int updateByPrimaryKeySelective(TopInfoDomain record);

    int updateByPrimaryKey(TopInfoDomain record);
}