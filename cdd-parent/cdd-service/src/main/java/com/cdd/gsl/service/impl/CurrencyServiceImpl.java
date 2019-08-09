package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.domain.UserInfoDomainExample;
import com.cdd.gsl.service.CurrencyService;
import com.cdd.gsl.vo.ConsumeRecordConditionVo;
import com.cdd.gsl.vo.ConsumeRecordVo;
import com.cdd.gsl.vo.CurrencyVo;
import com.cdd.gsl.vo.UserCurrencyVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

    private Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private UserCurrencyMappingDomainMapper userCurrencyMappingDomainMapper;

    @Autowired
    private UserCurrencyDao userCurrencyDao;

    @Autowired
    private ConsumeRecordDao consumeRecordDao;

    @Override
    public CommonResult currencyList() {
        List<CurrencyVo> currencyVoList = currencyDao.currencyList();
        CommonResult commonResult = new CommonResult();
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(currencyVoList);
        return commonResult;
    }

    public CommonResult isPublish(Long userId){
        logger.info("CurrencyServiceImpl isPublish userId -{}",userId);
        CommonResult commonResult = new CommonResult();
        List<UserCurrencyVo> userCurrencyVos = userCurrencyDao.findUserCurrencyByUserId(userId);
        if(userCurrencyVos != null && userCurrencyVos.size() > 0){
            UserCurrencyVo userCurrencyVo = userCurrencyVos.get(0);
            List<CurrencyVo> currencyVos = currencyDao.currencyListById(userCurrencyVo.getCurrencyId());
            if(CollectionUtils.isNotEmpty(currencyVos)){
                CurrencyVo currencyVo = currencyVos.get(0);
                int days = DateUtil.differentDaysByMillisecond(new Date(),userCurrencyVo.getCreateTs());
                if(days <= currencyVo.getMonth()*30){
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("可以发布");
                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("发布已到期请充值");
                }
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该币不存在");
            }
        }
        return commonResult;
    }

    @Override
    public CommonResult integralCount(Long userId) {
        logger.info("CurrencyServiceImpl integralCount userId -{}",userId);
        CommonResult commonResult = new CommonResult();
        UserInfoDomainExample userInfoDomainExample = new UserInfoDomainExample();
        userInfoDomainExample.createCriteria().andIdEqualTo(userId).andStatusEqualTo(1);
        List<UserInfoDomain> userInfoDomainList = userInfoDomainMapper.selectByExample(userInfoDomainExample);
        if(CollectionUtils.isNotEmpty(userInfoDomainList)){
            JSONObject json = new JSONObject();
            UserInfoDomain userInfoDomain = userInfoDomainList.get(0);
            Integer integral = userInfoDomain.getIntegral();
            json.put("integral",integral);
            List<UserCurrencyVo> userCurrencyVos = userCurrencyDao.findUserCurrencyByUserId(userId);
            if(userCurrencyVos != null && userCurrencyVos.size() > 0) {
                UserCurrencyVo userCurrencyVo = userCurrencyVos.get(0);
                List<CurrencyVo> currencyVos = currencyDao.currencyListById(userCurrencyVo.getCurrencyId());
                if (CollectionUtils.isNotEmpty(currencyVos)) {
                    CurrencyVo currencyVo = currencyVos.get(0);
                    int days = DateUtil.differentDaysByMillisecond(new Date(),userCurrencyVo.getCreateTs());
                    if(days < currencyVo.getMonth()*30){
                        json.put("days",currencyVo.getMonth()*30 - days);
                    }else {
                        json.put("days",null);
                    }
                }else{
                    json.put("days",null);
                }
            }else {
                json.put("days",null);
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(json);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("该用户不存在");
        }
        return commonResult;
    }

    @Override
    public CommonResult consumeRecord(ConsumeRecordConditionVo consumeRecordConditionVo) {
        logger.info("CurrencyServiceImpl consumeRecord userId -{}",consumeRecordConditionVo.getUserId());
        CommonResult commonResult = new CommonResult();
        try{
            List<ConsumeRecordVo> consumeRecordVoList = consumeRecordDao.selectConsumeRecordByUserId(consumeRecordConditionVo);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(consumeRecordVoList);
        }catch (Exception e){
            logger.error("CurrencyServiceImpl consumeRecord error");
            e.printStackTrace();
        }

        return commonResult;
    }
}
