package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CollectionHouseRecordDomain;
import com.cdd.gsl.domain.CollectionHouseRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionHouseRecordDomainMapper {
    int countByExample(CollectionHouseRecordDomainExample example);

    int deleteByExample(CollectionHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CollectionHouseRecordDomain record);

    int insertSelective(CollectionHouseRecordDomain record);

    List<CollectionHouseRecordDomain> selectByExample(CollectionHouseRecordDomainExample example);

    CollectionHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CollectionHouseRecordDomain record, @Param("example") CollectionHouseRecordDomainExample example);

    int updateByExample(@Param("record") CollectionHouseRecordDomain record, @Param("example") CollectionHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(CollectionHouseRecordDomain record);

    int updateByPrimaryKey(CollectionHouseRecordDomain record);
}