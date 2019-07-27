package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.ChatMessageService;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.ChatMessageVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
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
    private HouseInfoDao houseInfoDao;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    private EnterpriseInfoDomainMapper enterpriseInfoDomainMapper;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private SellParkDao sellParkDao;

    @Autowired
    private SellParkInfoDomainMapper sellParkInfoDomainMapper;

    @Autowired
    private LeaseParkInfoDomainMapper leaseParkInfoDomainMapper;

    @Autowired
    private LeaseParkDao leaseParkDao;

    @Autowired
    private HouseService houseService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private MessageInfoDao messageInfoDao;

    @Override
    public CommonResult addChat(ChatMessageDomain chatMessageDomain) {
        CommonResult commonResult = new CommonResult();
        if(chatMessageDomain != null){
            chatMessageDomainMapper.insertSelective(chatMessageDomain);
            MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
            messageInfoDomain.setUserId(chatMessageDomain.getReceiveUserId());
            messageInfoDomain.setMessage(chatMessageDomain.getMessageContent());
            messageInfoDomain.setMessageType("chat");
            messageInfoDomain.setType(chatMessageDomain.getType());
            messageInfoDomain.setObjId(chatMessageDomain.getObjId());
            messageInfoDomain.setSendUserId(chatMessageDomain.getSendUserId());
            messageInfoDomain.setReceiveUserId(chatMessageDomain.getReceiveUserId());
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
    public CommonResult findChatList(Long objId, String type,Long sendUserId,Long receiveUserId) {
        CommonResult commonResult = new CommonResult();
        if(objId != null && !StringUtils.isEmpty(type) && sendUserId != null && receiveUserId != null){
            JSONObject data = new JSONObject();
            if(type.equals("house")){
                HouseInfoDetailVo houseInfoDetailVo = houseService.findHouseInfoById(objId);
                data.put("item",houseInfoDetailVo);
            }else if(type.equals("enterprise")){
                CommonResult enterpriseCommon = enterpriseService.findEnterpriseDetail(objId);
                data.put("item",enterpriseCommon.getData());
            }else if(type.equals("sellPark")){
                SellParkInfoDomain sellParkInfoDomain = sellParkInfoDomainMapper.selectByPrimaryKey(objId);
                data.put("item",sellParkInfoDomain);
            }else if(type.equals("leasePark")){
                LeaseParkInfoDomain leaseParkInfoDomain = leaseParkInfoDomainMapper.selectByPrimaryKey(objId);
                data.put("item",leaseParkInfoDomain);
            }
            List<ChatMessageVo> chatMessageVoList = chatMessageDao.chatMessageList(objId,type,sendUserId,receiveUserId);
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
            data.put("chatMessageList",chatMessageVos);
            messageInfoDao.updateMessageIsRead(objId, type, sendUserId, receiveUserId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

    @Override
    public CommonResult findNewChatList(Long objId, String type,Long sendUserId,Long receiveUserId,int count) {
        CommonResult commonResult = new CommonResult();
        if(objId != null && !StringUtils.isEmpty(type) && sendUserId != null && receiveUserId != null){
            List<ChatMessageVo> allChatMessageList = chatMessageDao.chatMessageList(objId,type,sendUserId,receiveUserId);
            count = allChatMessageList.size() - count;
            List<ChatMessageVo> chatMessageVoList = chatMessageDao.newChatMessageList(objId,type,sendUserId,receiveUserId,count);
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
            messageInfoDao.updateMessageIsRead(objId, type, sendUserId, receiveUserId);
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
