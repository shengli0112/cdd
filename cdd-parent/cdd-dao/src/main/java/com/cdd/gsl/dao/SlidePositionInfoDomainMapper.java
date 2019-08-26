package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SlidePositionInfoDomain;
import com.cdd.gsl.domain.SlidePositionInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlidePositionInfoDomainMapper {
    int countByExample(SlidePositionInfoDomainExample example);

    int deleteByExample(SlidePositionInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SlidePositionInfoDomain record);

    int insertSelective(SlidePositionInfoDomain record);

    List<SlidePositionInfoDomain> selectByExample(SlidePositionInfoDomainExample example);

    SlidePositionInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SlidePositionInfoDomain record, @Param("example") SlidePositionInfoDomainExample example);

    int updateByExample(@Param("record") SlidePositionInfoDomain record, @Param("example") SlidePositionInfoDomainExample example);

    int updateByPrimaryKeySelective(SlidePositionInfoDomain record);

    int updateByPrimaryKey(SlidePositionInfoDomain record);
}