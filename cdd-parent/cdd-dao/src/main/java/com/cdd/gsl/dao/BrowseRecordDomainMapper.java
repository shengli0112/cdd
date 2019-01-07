package com.cdd.gsl.dao;

import com.cdd.gsl.domain.BrowseRecordDomain;
import com.cdd.gsl.domain.BrowseRecordDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrowseRecordDomainMapper {
    int countByExample(BrowseRecordDomainExample example);

    int deleteByExample(BrowseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BrowseRecordDomain record);

    int insertSelective(BrowseRecordDomain record);

    List<BrowseRecordDomain> selectByExample(BrowseRecordDomainExample example);

    BrowseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrowseRecordDomain record, @Param("example") BrowseRecordDomainExample example);

    int updateByExample(@Param("record") BrowseRecordDomain record, @Param("example") BrowseRecordDomainExample example);

    int updateByPrimaryKeySelective(BrowseRecordDomain record);

    int updateByPrimaryKey(BrowseRecordDomain record);
}