package com.cdd.gsl.dao;

import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.domain.AdminInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminInfoDomainMapper {
    int countByExample(AdminInfoDomainExample example);

    int deleteByExample(AdminInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminInfoDomain record);

    int insertSelective(AdminInfoDomain record);

    List<AdminInfoDomain> selectByExample(AdminInfoDomainExample example);

    AdminInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminInfoDomain record, @Param("example") AdminInfoDomainExample example);

    int updateByExample(@Param("record") AdminInfoDomain record, @Param("example") AdminInfoDomainExample example);

    int updateByPrimaryKeySelective(AdminInfoDomain record);

    int updateByPrimaryKey(AdminInfoDomain record);
}