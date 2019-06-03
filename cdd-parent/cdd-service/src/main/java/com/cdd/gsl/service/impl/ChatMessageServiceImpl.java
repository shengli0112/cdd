package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.ChatMessageDomainMapper;
import com.cdd.gsl.dao.UserInfoDao;
import com.cdd.gsl.domain.ChatMessageDomain;
import com.cdd.gsl.domain.ChatMessageDomainExample;
import com.cdd.gsl.service.ChatMessageService;
import com.cdd.gsl.vo.ChatMessageVo;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageDomainMapper chatMessageDomainMapper;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public CommonResult addChat(ChatMessageDomain chatMessageDomain) {
        CommonResult commonResult = new CommonResult();
        if(chatMessageDomain != null){
            chatMessageDomainMapper.insertSelective(chatMessageDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult findChatList(Long objId, String type) {
        CommonResult commonResult = new CommonResult();
        if(objId != null && !StringUtils.isEmpty(type)){
            ChatMessageDomainExample chatMessageDomainExample = new ChatMessageDomainExample();
            chatMessageDomainExample.createCriteria().andObjIdEqualTo(objId).andTypeEqualTo(type);
            List<ChatMessageDomain> chatMessageDomainList = chatMessageDomainMapper.selectByExample(chatMessageDomainExample);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<ChatMessageVo> chatMessageVoList = new ArrayList<>();
            if(chatMessageDomainList != null && chatMessageDomainList.size() > 0){
                for(ChatMessageDomain chatMessageDomain:chatMessageDomainList){
                    ChatMessageVo chatMessageVo = new ChatMessageVo();
                    chatMessageVo.setId(chatMessageDomain.getId());
                    chatMessageVo.setObjId(chatMessageDomain.getObjId());
                    chatMessageVo.setCreateTs(sdf.format(chatMessageDomain.getCreateTs()));
                    chatMessageVo.setMessageContent(chatMessageDomain.getMessageContent());
                    chatMessageVo.setType(chatMessageDomain.getType());
                    SingleUserInfoVo sendUser = userInfoDao.findUserInfoById(chatMessageDomain.getSendUserId());
                    chatMessageVo.setSendUser(sendUser);
                    SingleUserInfoVo receiveUser = userInfoDao.findUserInfoById(chatMessageDomain.getReceiveUserId());
                    chatMessageVo.setReceiveUser(receiveUser);
                    chatMessageVoList.add(chatMessageVo);
                }
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(chatMessageVoList);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }
}
