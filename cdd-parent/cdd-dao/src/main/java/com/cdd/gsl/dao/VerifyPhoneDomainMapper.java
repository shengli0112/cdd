package com.cdd.gsl.dao;

import com.cdd.gsl.domain.VerifyPhoneDomain;
import com.cdd.gsl.domain.VerifyPhoneDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VerifyPhoneDomainMapper {
    int countByExample(VerifyPhoneDomainExample example);

    int deleteByExample(VerifyPhoneDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VerifyPhoneDomain record);

    int insertSelective(VerifyPhoneDomain record);

    List<VerifyPhoneDomain> selectByExample(VerifyPhoneDomainExample example);

    VerifyPhoneDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VerifyPhoneDomain record, @Param("example") VerifyPhoneDomainExample example);

    int updateByExample(@Param("record") VerifyPhoneDomain record, @Param("example") VerifyPhoneDomainExample example);

    int updateByPrimaryKeySelective(VerifyPhoneDomain record);

    int updateByPrimaryKey(VerifyPhoneDomain record);
}