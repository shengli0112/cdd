package com.cdd.gsl.dao;

import com.cdd.gsl.domain.AdminTicketDomain;
import com.cdd.gsl.domain.AdminTicketDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminTicketDomainMapper {
    int countByExample(AdminTicketDomainExample example);

    int deleteByExample(AdminTicketDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminTicketDomain record);

    int insertSelective(AdminTicketDomain record);

    List<AdminTicketDomain> selectByExample(AdminTicketDomainExample example);

    AdminTicketDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminTicketDomain record, @Param("example") AdminTicketDomainExample example);

    int updateByExample(@Param("record") AdminTicketDomain record, @Param("example") AdminTicketDomainExample example);

    int updateByPrimaryKeySelective(AdminTicketDomain record);

    int updateByPrimaryKey(AdminTicketDomain record);
}