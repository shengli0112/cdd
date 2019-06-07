package com.cdd.gsl.dao;

import com.cdd.gsl.domain.ChatMessageDomain;
import com.cdd.gsl.domain.ChatMessageDomainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMessageDomainMapper {
    int countByExample(ChatMessageDomainExample example);

    int deleteByExample(ChatMessageDomainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChatMessageDomain record);

    int insertSelective(ChatMessageDomain record);

    List<ChatMessageDomain> selectByExample(ChatMessageDomainExample example);

    ChatMessageDomain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChatMessageDomain record, @Param("example") ChatMessageDomainExample example);

    int updateByExample(@Param("record") ChatMessageDomain record, @Param("example") ChatMessageDomainExample example);

    int updateByPrimaryKeySelective(ChatMessageDomain record);

    int updateByPrimaryKey(ChatMessageDomain record);
}