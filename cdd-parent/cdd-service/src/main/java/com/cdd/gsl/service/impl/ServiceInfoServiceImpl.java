package com.cdd.gsl.service.impl;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.domain.ServiceInfoDomainExample;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.service.ServiceInfoService;
import com.cdd.gsl.vo.EnterpriseInfoVo;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import com.cdd.gsl.vo.ServiceInfoVo;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService{

    private Logger logger = LoggerFactory.getLogger(ServiceInfoServiceImpl.class);

    @Autowired
    private ServiceInfoDomainMapper serviceInfoDomainMapper;

    @Autowired
    private ServiceInfoDao serviceInfoDao;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Override
    public CommonResult createServiceInfo(ServiceInfoDomain serviceInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            if(serviceInfoDomain != null){
                serviceInfoDomain.setCreateTs(new Date());
                serviceInfoDomain.setUpdateTs(new Date());
                serviceInfoDomain.setStatus(1);
                serviceInfoDomainMapper.insertSelective(serviceInfoDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("添加成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl createServiceInfo error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateServiceInfo(ServiceInfoDomain serviceInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try {
            if(serviceInfoDomain != null){
                serviceInfoDomainMapper.updateByPrimaryKeySelective(serviceInfoDomain);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("修改成功");
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl updateServiceInfo error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult deleteServiceInfo(Long userId, Long serviceId) {
        CommonResult commonResult = new CommonResult();
        try {
            ServiceInfoDomain serviceInfoDomain = new ServiceInfoDomain();
            serviceInfoDomain.setId(serviceId);
            serviceInfoDomain.setUserId(userId);
            serviceInfoDomain.setStatus(0);
            serviceInfoDomainMapper.updateByPrimaryKeySelective(serviceInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl deleteServiceInfo error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult findServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo) {
        CommonResult commonResult = new CommonResult();
        try {
            if(serviceInfoConditionVo != null){
                List<ServiceInfoVo> serviceInfoVoList = serviceInfoDao.getServiceInfoList(serviceInfoConditionVo);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(serviceInfoVoList);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl findServiceInfoList error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult findAdminServiceInfoList(AdminServiceConditionVo serviceInfoConditionVo) {
        CommonResult commonResult = new CommonResult();
        try {
            if(serviceInfoConditionVo != null){
                List<ServiceInfoVo> serviceInfoVoList = serviceInfoDao.getAdminServiceInfoList(serviceInfoConditionVo);
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(serviceInfoVoList);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl findServiceInfoList error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult checkPhone(Long userId,Long serviceId) {
        CommonResult commonResult = new CommonResult();
        try {
            if(userId != null){
                List<EnterpriseInfoVo> enterpriseInfoVos = enterpriseInfoDao.selectEnterpriseInfoListByUserId(userId);
                if(enterpriseInfoVos != null && enterpriseInfoVos.size() > 0){
                    EnterpriseInfoVo enterpriseInfoVo = enterpriseInfoVos.get(0);
                    String trade = enterpriseInfoVo.getTrade();
                    if(!StringUtils.isEmpty(trade)){
                        ServiceInfoDomainExample serviceInfoDomainExample = new ServiceInfoDomainExample();
                        serviceInfoDomainExample.createCriteria().andIdEqualTo(serviceId).andStatusEqualTo(1);
                        List<ServiceInfoDomain> serviceInfoDomainList = serviceInfoDomainMapper.selectByExample(serviceInfoDomainExample);
                        if(!CollectionUtils.isEmpty(serviceInfoDomainList)){
                            ServiceInfoDomain serviceInfoDomain = serviceInfoDomainList.get(0);
                            String serviceTypeName = serviceInfoDomain.getServiceTypeName();
                            String[] tradeArr = trade.split(",");
                            boolean isCheck = false;
                            for(String tradeStr:tradeArr){
                                if(serviceTypeName.contains(tradeStr)){
                                    isCheck = true;
                                }
                            }
                            if(isCheck){
                                UserInfoDomain singleUserInfoVo = userInfoDomainMapper.selectByPrimaryKey(userId);
                                Integer integral = singleUserInfoVo.getIntegral();
                                if(integral > CddConstant.PAY_INTERGAL_CHECK_PHONE){
                                    UserInfoDomain userInfoDomain = new UserInfoDomain();
                                    userInfoDomain.setId(userId);
                                    userInfoDomain.setIntegral(integral - CddConstant.PAY_INTERGAL_CHECK_PHONE);
                                    userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
                                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                                    commonResult.setMessage("允许查询");
                                }else{
                                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                                    commonResult.setMessage("多多币余额不足");
                                }

                            }else{
                                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                                commonResult.setMessage("您的服务行业不包含对应的需求类型");
                            }
                        }else{
                            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                            commonResult.setMessage("该企业需求已被删除");
                        }
                    }else{
                        commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                        commonResult.setMessage("请补全您企业的行业");
                    }

                }else{
                    commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                    commonResult.setMessage("请发布企业后查询");
                }

            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("参数不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("ServiceInfoServiceImpl findServiceInfoList error");
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }


}
