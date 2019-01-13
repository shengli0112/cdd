package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RecordInfoDomain;
import com.cdd.gsl.domain.RecordInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordInfoDomainMapper {
    int countByExample(RecordInfoDomainExample example);

    int deleteByExample(RecordInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RecordInfoDomain record);

    int insertSelective(RecordInfoDomain record);

    List<RecordInfoDomain> selectByExample(RecordInfoDomainExample example);

    RecordInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RecordInfoDomain record, @Param("example") RecordInfoDomainExample example);

    int updateByExample(@Param("record") RecordInfoDomain record, @Param("example") RecordInfoDomainExample example);

    int updateByPrimaryKeySelective(RecordInfoDomain record);

    int updateByPrimaryKey(RecordInfoDomain record);
}