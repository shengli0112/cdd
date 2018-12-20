package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CollectionLeaseHouseRecordDomain;
import com.cdd.gsl.domain.CollectionLeaseHouseRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionLeaseHouseRecordDomainMapper {
    int countByExample(CollectionLeaseHouseRecordDomainExample example);

    int deleteByExample(CollectionLeaseHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CollectionLeaseHouseRecordDomain record);

    int insertSelective(CollectionLeaseHouseRecordDomain record);

    List<CollectionLeaseHouseRecordDomain> selectByExample(CollectionLeaseHouseRecordDomainExample example);

    CollectionLeaseHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CollectionLeaseHouseRecordDomain record, @Param("example") CollectionLeaseHouseRecordDomainExample example);

    int updateByExample(@Param("record") CollectionLeaseHouseRecordDomain record, @Param("example") CollectionLeaseHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(CollectionLeaseHouseRecordDomain record);

    int updateByPrimaryKey(CollectionLeaseHouseRecordDomain record);
}