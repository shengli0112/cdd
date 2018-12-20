package com.cdd.gsl.dao;

import com.cdd.gsl.domain.InformLeaseHouseRecordDomain;
import com.cdd.gsl.domain.InformLeaseHouseRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InformLeaseHouseRecordDomainMapper {
    int countByExample(InformLeaseHouseRecordDomainExample example);

    int deleteByExample(InformLeaseHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InformLeaseHouseRecordDomain record);

    int insertSelective(InformLeaseHouseRecordDomain record);

    List<InformLeaseHouseRecordDomain> selectByExample(InformLeaseHouseRecordDomainExample example);

    InformLeaseHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InformLeaseHouseRecordDomain record, @Param("example") InformLeaseHouseRecordDomainExample example);

    int updateByExample(@Param("record") InformLeaseHouseRecordDomain record, @Param("example") InformLeaseHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(InformLeaseHouseRecordDomain record);

    int updateByPrimaryKey(InformLeaseHouseRecordDomain record);
}