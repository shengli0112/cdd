package com.cdd.gsl.dao;

import com.cdd.gsl.domain.InformHouseRecordDomain;
import com.cdd.gsl.domain.InformHouseRecordDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InformHouseRecordDomainMapper {
    int countByExample(InformHouseRecordDomainExample example);

    int deleteByExample(InformHouseRecordDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InformHouseRecordDomain record);

    int insertSelective(InformHouseRecordDomain record);

    List<InformHouseRecordDomain> selectByExample(InformHouseRecordDomainExample example);

    InformHouseRecordDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InformHouseRecordDomain record, @Param("example") InformHouseRecordDomainExample example);

    int updateByExample(@Param("record") InformHouseRecordDomain record, @Param("example") InformHouseRecordDomainExample example);

    int updateByPrimaryKeySelective(InformHouseRecordDomain record);

    int updateByPrimaryKey(InformHouseRecordDomain record);
}