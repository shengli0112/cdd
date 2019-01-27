package com.cdd.gsl.dao;

import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.domain.MessageInfoDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageInfoDomainMapper {
    int countByExample(MessageInfoDomainExample example);

    int deleteByExample(MessageInfoDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageInfoDomain record);

    int insertSelective(MessageInfoDomain record);

    List<MessageInfoDomain> selectByExample(MessageInfoDomainExample example);

    MessageInfoDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageInfoDomain record, @Param("example") MessageInfoDomainExample example);

    int updateByExample(@Param("record") MessageInfoDomain record, @Param("example") MessageInfoDomainExample example);

    int updateByPrimaryKeySelective(MessageInfoDomain record);

    int updateByPrimaryKey(MessageInfoDomain record);
}