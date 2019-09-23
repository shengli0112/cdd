package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.HttpClientUtils;
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
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    private Logger logger = LoggerFactory.getLogger(ParkServiceImpl.class);

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

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Value("${verify.code.url}")
    private String verifyCodeUrl;

    @Value("${verify.code.key}")
    private String verifyCodeKey;

    @Value("${verify.warn.id}")
    private String verifyWarnId;

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
            if(Strings.isNotEmpty(enterpriseInfoDomain.getPhone())){
                logger.info("企业圈提醒短信发送开始");
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("企业圈提醒短信 createEnterprise enterpriseInfoDomain -{}",enterpriseInfoDomain.toString());
                        sendSms(enterpriseInfoDomain);
                    }
                });
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    //出售短信提醒
    public void sendSms(EnterpriseInfoDomain enterpriseInfoDomain){
        String tplValue = null;
        try {
            StringBuffer address = new StringBuffer();
            address.append(enterpriseInfoDomain.getEnterpriseName()).append(enterpriseInfoDomain.getMainBusiness());

            SingleUserInfoVo user = userInfoDao.findUserInfoById(enterpriseInfoDomain.getUserId());
            String userStr = user.getUsername()+" "+user.getPhone();
            tplValue = URLEncoder.encode("#code#="+address.toString()+"&#name#="+userStr+"&#content#=网址：http://cddwang.com，欢迎点击网址进入平台查看或发布信息。","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer uri = new StringBuffer().append(verifyCodeUrl)
                .append("?mobile=").append(enterpriseInfoDomain.getPhone()).append("&tpl_id=").append(verifyWarnId)
                .append("&tpl_value=").append(tplValue).append("&key=").append(verifyCodeKey);
        String response = HttpClientUtils.getInstance().doGetWithJsonResult(uri.toString());
        if(Strings.isNotEmpty(response)){
            JSONObject res = JSONObject.parseObject(response);
            Integer flag = res.getInteger("error_code");
            if(flag == 0){
                logger.info("createEnterprise 验证码发送成功");
            }else{
                logger.info("createEnterprise 验证码发送失败");
            }
        }else{
            logger.info("createEnterprise 验证码发送失败");
        }

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
