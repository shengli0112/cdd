package com.cdd.gsl.dao;

import com.cdd.gsl.domain.StorageInfoDomain;
import com.cdd.gsl.domain.StorageInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageInfoDomainMapper {
    int countByExample(StorageInfoDomainExample example);

    int deleteByExample(StorageInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StorageInfoDomain record);

    int insertSelective(StorageInfoDomain record);

    List<StorageInfoDomain> selectByExample(StorageInfoDomainExample example);

    StorageInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StorageInfoDomain record, @Param("example") StorageInfoDomainExample example);

    int updateByExample(@Param("record") StorageInfoDomain record, @Param("example") StorageInfoDomainExample example);

    int updateByPrimaryKeySelective(StorageInfoDomain record);

    int updateByPrimaryKey(StorageInfoDomain record);
}