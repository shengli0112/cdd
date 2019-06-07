package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ChatMessageDomain;

public interface ChatMessageService {
    public CommonResult addChat(ChatMessageDomain chatMessageDomain);

    public CommonResult findChatList(Long objId,String type,Long userId);
}
