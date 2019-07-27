package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ChatMessageDomain;
import com.cdd.gsl.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    @RequestMapping("addChat")
    public CommonResult addChat(@RequestBody ChatMessageDomain chatMessageDomain){
        CommonResult commonResult = chatMessageService.addChat(chatMessageDomain);
        return commonResult;
    }

    @RequestMapping("findChatList")
    public CommonResult findChatList(@RequestParam("objId") Long objId,@RequestParam("type")String type,
                                     @RequestParam("sendUserId")Long sendUserId,@RequestParam("receiveUserId")Long receiveUserId){
        CommonResult commonResult = chatMessageService.findChatList(objId,type,sendUserId,receiveUserId);
        return commonResult;
    }

    @RequestMapping("findNewChatList")
    public CommonResult findNewChatList(@RequestParam("objId") Long objId,@RequestParam("type")String type,
                                     @RequestParam("sendUserId")Long sendUserId,@RequestParam("receiveUserId")Long receiveUserId,@RequestParam("count")Integer count){
        CommonResult commonResult = chatMessageService.findNewChatList(objId,type,sendUserId,receiveUserId,count);
        return commonResult;
    }
}
