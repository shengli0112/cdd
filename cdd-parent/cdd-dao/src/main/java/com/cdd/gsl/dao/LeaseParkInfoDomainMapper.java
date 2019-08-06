package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.LeaseParkInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaseParkInfoDomainMapper {
    int countByExample(LeaseParkInfoDomainExample example);

    int deleteByExample(LeaseParkInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LeaseParkInfoDomain record);

    int insertSelective(LeaseParkInfoDomain record);

    List<LeaseParkInfoDomain> selectByExampleWithBLOBs(LeaseParkInfoDomainExample example);

    List<LeaseParkInfoDomain> selectByExample(LeaseParkInfoDomainExample example);

    LeaseParkInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LeaseParkInfoDomain record, @Param("example") LeaseParkInfoDomainExample example);

    int updateByExampleWithBLOBs(@Param("record") LeaseParkInfoDomain record, @Param("example") LeaseParkInfoDomainExample example);

    int updateByExample(@Param("record") LeaseParkInfoDomain record, @Param("example") LeaseParkInfoDomainExample example);

    int updateByPrimaryKeySelective(LeaseParkInfoDomain record);

    int updateByPrimaryKeyWithBLOBs(LeaseParkInfoDomain record);

    int updateByPrimaryKey(LeaseParkInfoDomain record);
}