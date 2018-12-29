package com.cdd.gsl.dao;

import com.cdd.gsl.domain.RoleMenuMappingDomain;
import com.cdd.gsl.domain.RoleMenuMappingDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMappingDomainMapper {
    int countByExample(RoleMenuMappingDomainExample example);

    int deleteByExample(RoleMenuMappingDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleMenuMappingDomain record);

    int insertSelective(RoleMenuMappingDomain record);

    List<RoleMenuMappingDomain> selectByExample(RoleMenuMappingDomainExample example);

    RoleMenuMappingDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleMenuMappingDomain record, @Param("example") RoleMenuMappingDomainExample example);

    int updateByExample(@Param("record") RoleMenuMappingDomain record, @Param("example") RoleMenuMappingDomainExample example);

    int updateByPrimaryKeySelective(RoleMenuMappingDomain record);

    int updateByPrimaryKey(RoleMenuMappingDomain record);
}