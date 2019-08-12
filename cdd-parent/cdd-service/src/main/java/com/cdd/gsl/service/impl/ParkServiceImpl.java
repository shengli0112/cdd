package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ParkAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.ConsumeRecordDomain;
import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.service.ParkService;
import com.cdd.gsl.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    private Logger logger = LoggerFactory.getLogger(ParkServiceImpl.class);

    @Autowired
    private SellParkInfoDomainMapper sellParkInfoDomainMapper;

    @Autowired
    private LeaseParkInfoDomainMapper leaseParkInfoDomainMapper;

    @Autowired
    private SellParkDao sellParkDao;

    @Autowired
    private LeaseParkDao leaseParkDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Override
    public CommonResult createSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        logger.info("ParkServiceImpl createSellPark start");
        try{
            if(sellParkInfoDomain != null){
                logger.info("ParkServiceImpl createSellPark sellParkInfoDomain -{}",JSONObject.toJSON(sellParkInfoDomain).toString());
                sellParkInfoDomainMapper.insertSelective(sellParkInfoDomain);
                userInfoDao.updateUserintegralById(sellParkInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                messageInfoDomain.setUserId(sellParkInfoDomain.getUserId());
                messageInfoDomain.setMessage("您发布园区\""+sellParkInfoDomain.getParkName()+"\"成功，奖励多多币5枚");
                messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
                ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                consumeRecordDomain.setTitle(CddConstant.CREATE_ENTERPRISE_TITLE);
                consumeRecordDomain.setUserId(sellParkInfoDomain.getUserId());
                consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
                consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
                consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
                consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("创建成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不正确");
            }
        }catch (Exception e){
            logger.error("ParkServiceImpl createSellPark error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }

        return commonResult;
    }

    @Override
    public CommonResult createLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try{
            if(leaseParkInfoDomain != null){
                logger.info("ParkServiceImpl createLeasePark leaseParkInfoDomain -{}",JSONObject.toJSON(leaseParkInfoDomain).toString());
                leaseParkInfoDomainMapper.insertSelective(leaseParkInfoDomain);
                userInfoDao.updateUserintegralById(leaseParkInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
                /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
                messageInfoDomain.setUserId(leaseParkInfoDomain.getUserId());
                messageInfoDomain.setMessage("您发布园区\""+leaseParkInfoDomain.getParkName()+"\"成功，奖励多多币5枚");
                messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
                messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
                ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
                consumeRecordDomain.setTitle(CddConstant.CREATE_ENTERPRISE_TITLE);
                consumeRecordDomain.setUserId(leaseParkInfoDomain.getUserId());
                consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
                consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
                consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
                consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("创建成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不正确");
            }
        }catch (Exception e){
            logger.error("ParkServiceImpl createLeasePark error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }

        return commonResult;
    }

    @Override
    public CommonResult updateSellPark(SellParkInfoDomain sellParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(sellParkInfoDomain != null){
            sellParkInfoDomainMapper.updateByPrimaryKeySelective(sellParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateLeasePark(LeaseParkInfoDomain leaseParkInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(leaseParkInfoDomain != null){
            leaseParkInfoDomainMapper.updateByPrimaryKeySelective(leaseParkInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult findSellParkDetail(Long sellParkId) {
        CommonResult  commonResult = new CommonResult<>();
        if(sellParkId != null){
            List<SellParkInfoVo> sellParkInfoVoList = sellParkDao.selectSellParkInfoById(sellParkId);
            if(CollectionUtils.isNotEmpty(sellParkInfoVoList)){
                SellParkInfoVo sellParkInfoVo = sellParkInfoVoList.get(0);
                SingleUserInfoVo userInfoVo = userInfoDao.findUserInfoById(sellParkInfoVo.getUserId());
                sellParkInfoVo.setUser(userInfoVo);
                List<SellParkInfoVo> sellParkInfoVoRand = sellParkDao.selectSellParkRand();
                sellParkInfoVo.setLikes(sellParkInfoVoRand);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(sellParkInfoVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该出售园区不存在");
            }


        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<LeaseParkInfoVo> findLeaseParkDetail(Long leaseParkId) {
        CommonResult<LeaseParkInfoVo>  commonResult = new CommonResult<>();
        if(leaseParkId != null){
            List<LeaseParkInfoVo> leaseParkInfos = leaseParkDao.selectLeaseParkInfoById(leaseParkId);
            LeaseParkInfoVo leaseParkInfoVo = new LeaseParkInfoVo();
            if(leaseParkInfos != null && leaseParkInfos.size() > 0){
                leaseParkInfoVo = leaseParkInfos.get(0);
                SingleUserInfoVo userInfoVo = userInfoDao.findUserInfoById(leaseParkInfoVo.getUserId());
                leaseParkInfoVo.setUser(userInfoVo);
                List<LeaseParkInfoVo> randomLeaseParkList = leaseParkDao.selectLeaseParkInfoByRandom();
                leaseParkInfoVo.setLikes(randomLeaseParkList);
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(leaseParkInfoVo);

        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public List<SellParkInfoDomain> findSellParkList(SellParkCondition sellParkCondition) {
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectSellParkInfoList(sellParkCondition);
        return sellParkInfoDomainList;
    }

    @Override
    public List<LeaseParkInfoVo> findLeaseParkList(LeaseParkCondition leaseParkCondition) {
        return leaseParkDao.selectLeaseParkInfoList(leaseParkCondition);
    }

    @Override
    public CommonResult findAdminSellParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = sellParkDao.sellParkCount(parkAdminConditionVo);
        List<SellParkInfoDomain> sellParkInfoDomainList = sellParkDao.selectAdminSellParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("sellParkList",sellParkInfoDomainList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult findAdminLeaseParkList(ParkAdminConditionVo parkAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        int total = leaseParkDao.leaseParkCount(parkAdminConditionVo);
        List<LeaseParkInfoVo> leaseParkInfoVoList = leaseParkDao.selectAdminLeaseParkInfoList(parkAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("leaseParkList",leaseParkInfoVoList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult deleteSellPark(Long sellParkId) {
        return null;
    }

    @Override
    public CommonResult deleteLeasePark(Long leaseParkId) {
        return null;
    }
}
