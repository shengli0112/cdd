package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ConsumeRecordDomain;
import com.cdd.gsl.domain.ConsumeRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsumeRecordDomainMapper {
    int countByExample(ConsumeRecordDomainExample example);

    int deleteByExample(ConsumeRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ConsumeRecordDomain record);

    int insertSelective(ConsumeRecordDomain record);

    List<ConsumeRecordDomain> selectByExample(ConsumeRecordDomainExample example);

    ConsumeRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ConsumeRecordDomain record, @Param("example") ConsumeRecordDomainExample example);

    int updateByExample(@Param("record") ConsumeRecordDomain record, @Param("example") ConsumeRecordDomainExample example);

    int updateByPrimaryKeySelective(ConsumeRecordDomain record);

    int updateByPrimaryKey(ConsumeRecordDomain record);
}