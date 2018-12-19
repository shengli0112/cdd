package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RoleInfoDomain;
import com.cdd.gsl.domain.RoleInfoDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleInfoDomainMapper {
    int countByExample(RoleInfoDomainExample example);

    int deleteByExample(RoleInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleInfoDomain record);

    int insertSelective(RoleInfoDomain record);

    List<RoleInfoDomain> selectByExample(RoleInfoDomainExample example);

    RoleInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleInfoDomain record, @Param("example") RoleInfoDomainExample example);

    int updateByExample(@Param("record") RoleInfoDomain record, @Param("example") RoleInfoDomainExample example);

    int updateByPrimaryKeySelective(RoleInfoDomain record);

    int updateByPrimaryKey(RoleInfoDomain record);
}