package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserTicketDomain;
import com.cdd.gsl.domain.UserTicketDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTicketDomainMapper {
    int countByExample(UserTicketDomainExample example);

    int deleteByExample(UserTicketDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserTicketDomain record);

    int insertSelective(UserTicketDomain record);

    List<UserTicketDomain> selectByExample(UserTicketDomainExample example);

    UserTicketDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserTicketDomain record, @Param("example") UserTicketDomainExample example);

    int updateByExample(@Param("record") UserTicketDomain record, @Param("example") UserTicketDomainExample example);

    int updateByPrimaryKeySelective(UserTicketDomain record);

    int updateByPrimaryKey(UserTicketDomain record);
}