package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.ConsumeRecordDomain;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.domain.MessageInfoDomain;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.vo.EnterpriseAdminConditionVo;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import com.cdd.gsl.vo.EnterpriseInfoVo;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseInfoDomainMapper enterpriseInfoDomainMapper;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MessageInfoDomainMapper messageInfoDomainMapper;

    @Autowired
    private ConsumeRecordDomainMapper consumeRecordDomainMapper;

    @Override
    public CommonResult createEnterprise(EnterpriseInfoDomain enterpriseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(enterpriseInfoDomain != null){
            enterpriseInfoDomainMapper.insertSelective(enterpriseInfoDomain);
            userInfoDao.updateUserintegralById(enterpriseInfoDomain.getUserId(),CddConstant.AWARD_CURRENCY_COUNT);
            /*MessageInfoDomain messageInfoDomain = new MessageInfoDomain();
            messageInfoDomain.setUserId(enterpriseInfoDomain.getUserId());
            messageInfoDomain.setMessage("您发布企业圈\""+enterpriseInfoDomain.getTitle()+"\"成功，奖励多多币5枚");
            messageInfoDomain.setMessageType(CddConstant.MESSAGE_CURRENCY_TYPE);
            messageInfoDomainMapper.insertSelective(messageInfoDomain);*/
            ConsumeRecordDomain consumeRecordDomain = new ConsumeRecordDomain();
            consumeRecordDomain.setTitle(CddConstant.CREATE_ENTERPRISE_TITLE);
            consumeRecordDomain.setUserId(enterpriseInfoDomain.getUserId());
            consumeRecordDomain.setAction(CddConstant.CONSUME_RECORD_AWARD);
            consumeRecordDomain.setIntegral(CddConstant.AWARD_CURRENCY_COUNT);
            consumeRecordDomain.setType(CddConstant.CONSUME_RECORD_TYPE_ADD_HOUSE);
            consumeRecordDomainMapper.insertSelective(consumeRecordDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("创建企业成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateEnterprise(EnterpriseInfoDomain enterpriseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(enterpriseInfoDomain != null){
            enterpriseInfoDomainMapper.updateByPrimaryKeySelective(enterpriseInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("更新企业成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult deleteEnterprise(Long enterpriseId, Long userId) {
        CommonResult commonResult = new CommonResult();
        if(enterpriseId != null && userId != null){
            enterpriseInfoDao.deleteEnterpriseInfoByIdAndUserId(enterpriseId,userId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除企业成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult<List<EnterpriseInfoDomain>> findEnterpriseInfoList(EnterpriseConditionVo enterpriseConditionVo) {
        CommonResult<List<EnterpriseInfoDomain>> commonResult = new CommonResult<>();
        List<EnterpriseInfoDomain> enterpriseInfoDomainList = enterpriseInfoDao.selectEnterpriseInfoListByCondition(enterpriseConditionVo);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(enterpriseInfoDomainList);
        return commonResult;
    }

    @Override
    public CommonResult findEnterpriseDetail(Long enterpriseId) {
        CommonResult commonResult = new CommonResult();
        if(enterpriseId != null){
            List<EnterpriseInfoVo> enterpriseInfoVoList = enterpriseInfoDao.selectAdminEnterpriseInfoListById(enterpriseId);
            if(CollectionUtils.isNotEmpty(enterpriseInfoVoList)){
                EnterpriseInfoVo enterpriseInfoVo = enterpriseInfoVoList.get(0);
                SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(enterpriseInfoVo.getUserId());
                enterpriseInfoVo.setUser(singleUserInfoVo);
                List<EnterpriseInfoVo> randEnterpriseList = enterpriseInfoDao.selectEnterpriseInfoListRand();
                enterpriseInfoVo.setLikes(randEnterpriseList);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(enterpriseInfoVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该企业被删除");
            }

        }else{
            commonResult.setMessage("参数异常");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
        }
        return commonResult;
    }

    @Override
    public CommonResult findEnterpriseAdminList(EnterpriseAdminConditionVo enterpriseAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        List<EnterpriseInfoVo> enterpriseInfoVoList = enterpriseInfoDao.selectAdminEnterpriseInfoListByCondition(enterpriseAdminConditionVo);
        int count = enterpriseInfoDao.enterpriseCount(enterpriseAdminConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",count);
        json.put("enterpriseList",enterpriseInfoVoList);

        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }
}
