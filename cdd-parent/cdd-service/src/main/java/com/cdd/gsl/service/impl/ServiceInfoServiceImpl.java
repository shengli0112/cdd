package com.cdd.gsl.service.impl;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.ServiceInfoDomain;
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
    public CommonResult checkPhone(Long userId) {
        CommonResult commonResult = new CommonResult();
        try {
            if(userId != null){
                List<EnterpriseInfoVo> enterpriseInfoVos = enterpriseInfoDao.selectEnterpriseInfoListByUserId(userId);
                if(enterpriseInfoVos != null && enterpriseInfoVos.size() > 0){
                    UserInfoDomain singleUserInfoVo = userInfoDomainMapper.selectByPrimaryKey(userId);
                    Integer integral = singleUserInfoVo.getIntegral();
                    if(integral > CddConstant.PAY_INTERGAL_CHECK_PHONE){
                        UserInfoDomain userInfoDomain = new UserInfoDomain();
                        userInfoDomain.setId(userId);
                        userInfoDomain.setIntegral(integral - CddConstant.PAY_INTERGAL_CHECK_PHONE);
                        userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
                    }
                    commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                    commonResult.setMessage("允许查询");
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
