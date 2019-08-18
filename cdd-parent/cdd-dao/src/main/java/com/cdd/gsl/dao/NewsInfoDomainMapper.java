package com.cdd.gsl.dao;

import com.cdd.gsl.domain.NewsInfoDomain;
import com.cdd.gsl.domain.NewsInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsInfoDomainMapper {
    int countByExample(NewsInfoDomainExample example);

    int deleteByExample(NewsInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NewsInfoDomain record);

    int insertSelective(NewsInfoDomain record);

    List<NewsInfoDomain> selectByExampleWithBLOBs(NewsInfoDomainExample example);

    List<NewsInfoDomain> selectByExample(NewsInfoDomainExample example);

    NewsInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NewsInfoDomain record, @Param("example") NewsInfoDomainExample example);

    int updateByExampleWithBLOBs(@Param("record") NewsInfoDomain record, @Param("example") NewsInfoDomainExample example);

    int updateByExample(@Param("record") NewsInfoDomain record, @Param("example") NewsInfoDomainExample example);

    int updateByPrimaryKeySelective(NewsInfoDomain record);

    int updateByPrimaryKeyWithBLOBs(NewsInfoDomain record);

    int updateByPrimaryKey(NewsInfoDomain record);
}