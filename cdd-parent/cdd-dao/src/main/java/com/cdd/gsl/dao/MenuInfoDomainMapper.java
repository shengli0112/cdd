package com.cdd.gsl.dao;

import com.cdd.gsl.domain.MenuInfoDomain;
import com.cdd.gsl.domain.MenuInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuInfoDomainMapper {
    int countByExample(MenuInfoDomainExample example);

    int deleteByExample(MenuInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuInfoDomain record);

    int insertSelective(MenuInfoDomain record);

    List<MenuInfoDomain> selectByExample(MenuInfoDomainExample example);

    MenuInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenuInfoDomain record, @Param("example") MenuInfoDomainExample example);

    int updateByExample(@Param("record") MenuInfoDomain record, @Param("example") MenuInfoDomainExample example);

    int updateByPrimaryKeySelective(MenuInfoDomain record);

    int updateByPrimaryKey(MenuInfoDomain record);
}