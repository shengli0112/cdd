package com.cdd.gsl.dao;

import com.cdd.gsl.domain.OpenScreenInfoDomain;
import com.cdd.gsl.domain.OpenScreenInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpenScreenInfoDomainMapper {
    int countByExample(OpenScreenInfoDomainExample example);

    int deleteByExample(OpenScreenInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpenScreenInfoDomain record);

    int insertSelective(OpenScreenInfoDomain record);

    List<OpenScreenInfoDomain> selectByExample(OpenScreenInfoDomainExample example);

    OpenScreenInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpenScreenInfoDomain record, @Param("example") OpenScreenInfoDomainExample example);

    int updateByExample(@Param("record") OpenScreenInfoDomain record, @Param("example") OpenScreenInfoDomainExample example);

    int updateByPrimaryKeySelective(OpenScreenInfoDomain record);

    int updateByPrimaryKey(OpenScreenInfoDomain record);
}