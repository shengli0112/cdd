package com.cdd.gsl.dao;

import com.cdd.gsl.domain.BrowseLeaseHouseRecordDomain;
import com.cdd.gsl.domain.BrowseLeaseHouseRecordDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrowseLeaseHouseRecordDomainMapper {
    int countByExample(BrowseLeaseHouseRecordDomainExample example);

    int deleteByExample(BrowseLeaseHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BrowseLeaseHouseRecordDomain record);

    int insertSelective(BrowseLeaseHouseRecordDomain record);

    List<BrowseLeaseHouseRecordDomain> selectByExample(BrowseLeaseHouseRecordDomainExample example);

    BrowseLeaseHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrowseLeaseHouseRecordDomain record, @Param("example") BrowseLeaseHouseRecordDomainExample example);

    int updateByExample(@Param("record") BrowseLeaseHouseRecordDomain record, @Param("example") BrowseLeaseHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(BrowseLeaseHouseRecordDomain record);

    int updateByPrimaryKey(BrowseLeaseHouseRecordDomain record);
}