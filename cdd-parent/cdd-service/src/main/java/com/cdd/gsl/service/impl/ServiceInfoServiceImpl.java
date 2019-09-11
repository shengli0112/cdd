package com.cdd.gsl.service.impl;

import com.cdd.gsl.admin.AdminServiceConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.ServiceInfoDao;
import com.cdd.gsl.dao.ServiceInfoDomainMapper;
import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.service.ServiceInfoService;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import com.cdd.gsl.vo.ServiceInfoVo;
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
}
