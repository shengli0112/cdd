package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CommonDictDomain;
import com.cdd.gsl.domain.CommonDictDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonDictDomainMapper {
    int countByExample(CommonDictDomainExample example);

    int deleteByExample(CommonDictDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommonDictDomain record);

    int insertSelective(CommonDictDomain record);

    List<CommonDictDomain> selectByExample(CommonDictDomainExample example);

    CommonDictDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommonDictDomain record, @Param("example") CommonDictDomainExample example);

    int updateByExample(@Param("record") CommonDictDomain record, @Param("example") CommonDictDomainExample example);

    int updateByPrimaryKeySelective(CommonDictDomain record);

    int updateByPrimaryKey(CommonDictDomain record);
}