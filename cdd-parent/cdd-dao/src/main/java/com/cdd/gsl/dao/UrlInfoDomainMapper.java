package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UrlInfoDomain;
import com.cdd.gsl.domain.UrlInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UrlInfoDomainMapper {
    int countByExample(UrlInfoDomainExample example);

    int deleteByExample(UrlInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UrlInfoDomain record);

    int insertSelective(UrlInfoDomain record);

    List<UrlInfoDomain> selectByExample(UrlInfoDomainExample example);

    UrlInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UrlInfoDomain record, @Param("example") UrlInfoDomainExample example);

    int updateByExample(@Param("record") UrlInfoDomain record, @Param("example") UrlInfoDomainExample example);

    int updateByPrimaryKeySelective(UrlInfoDomain record);

    int updateByPrimaryKey(UrlInfoDomain record);
}