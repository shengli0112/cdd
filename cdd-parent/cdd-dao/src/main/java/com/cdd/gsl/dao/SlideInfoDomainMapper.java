package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.domain.SlideInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlideInfoDomainMapper {
    int countByExample(SlideInfoDomainExample example);

    int deleteByExample(SlideInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SlideInfoDomain record);

    int insertSelective(SlideInfoDomain record);

    List<SlideInfoDomain> selectByExample(SlideInfoDomainExample example);

    SlideInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SlideInfoDomain record, @Param("example") SlideInfoDomainExample example);

    int updateByExample(@Param("record") SlideInfoDomain record, @Param("example") SlideInfoDomainExample example);

    int updateByPrimaryKeySelective(SlideInfoDomain record);

    int updateByPrimaryKey(SlideInfoDomain record);
}