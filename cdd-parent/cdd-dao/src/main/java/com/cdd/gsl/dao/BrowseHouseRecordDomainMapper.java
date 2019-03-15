package com.cdd.gsl.dao;

import com.cdd.gsl.domain.BrowseHouseRecordDomain;
import com.cdd.gsl.domain.BrowseHouseRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrowseHouseRecordDomainMapper {
    int countByExample(BrowseHouseRecordDomainExample example);

    int deleteByExample(BrowseHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BrowseHouseRecordDomain record);

    int insertSelective(BrowseHouseRecordDomain record);

    List<BrowseHouseRecordDomain> selectByExample(BrowseHouseRecordDomainExample example);

    BrowseHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrowseHouseRecordDomain record, @Param("example") BrowseHouseRecordDomainExample example);

    int updateByExample(@Param("record") BrowseHouseRecordDomain record, @Param("example") BrowseHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(BrowseHouseRecordDomain record);

    int updateByPrimaryKey(BrowseHouseRecordDomain record);
}