package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.ChatMessageDao;
import com.cdd.gsl.dao.ChatMessageDomainMapper;
import com.cdd.gsl.dao.MessageInfoDomainMapper;
import com.cdd.gsl.dao.UserInfoDao;
import com.cdd.gsl.domain.ChatMessageDomain;
import com.cdd.gsl.domain.ChatMessageDomainExample;
import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.service.ChatMessageService;
import com.cdd.gsl.vo.ChatMessageVo;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageDomainMapper chatMessageDomainMapper;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Override
    public CommonResult addChat(ChatMessageDomain chatMessageDomain) {
        CommonResult commonResult = new CommonResult();
        if(chatMessageDomain != null){
            chatMessageDomainMapper.insertSelective(chatMessageDomain);
            MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
            messageInfoDomain.setUserId(chatMessageDomain.getReceiveUserId());
            messageInfoDomain.setMessage(CddConstant.MESSAGE_CONTENT);
            messageInfoDomain.setMessageType("chat");
            messageInfoDomain.setType(chatMessageDomain.getType());
            messageInfoDomain.setObjId(chatMessageDomain.getObjId());
            messageInfoDomainMapper.insertSelective(messageInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult findChatList(Long objId, String type,Long userId) {
        CommonResult commonResult = new CommonResult();
        if(objId != null && !StringUtils.isEmpty(type) && userId != null){

            List<ChatMessageVo> chatMessageVoList = chatMessageDao.chatMessageList(objId,type,userId);
            List<ChatMessageVo> chatMessageVos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(chatMessageVoList)){
                for(ChatMessageVo chatMessageVo:chatMessageVoList){
                    SingleUserInfoVo sendUser = userInfoDao.findUserInfoById(chatMessageVo.getSendUserId());
                    SingleUserInfoVo receiveUser = userInfoDao.findUserInfoById(chatMessageVo.getReceiveUserId());
                    chatMessageVo.setSendUser(sendUser);
                    chatMessageVo.setReceiveUser(receiveUser);
                    chatMessageVos.add(chatMessageVo);
                }
            }

            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(chatMessageVos);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }
}
