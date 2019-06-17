package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.EnterpriseInfoDao;
import com.cdd.gsl.dao.EnterpriseInfoDomainMapper;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.vo.EnterpriseAdminConditionVo;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import com.cdd.gsl.vo.EnterpriseInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseInfoDomainMapper enterpriseInfoDomainMapper;

    @Autowired
    private EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public CommonResult createEnterprise(EnterpriseInfoDomain enterpriseInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(enterpriseInfoDomain != null){
            enterpriseInfoDomainMapper.insertSelective(enterpriseInfoDomain);
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
            EnterpriseInfoDomain enterpriseInfoDomain = enterpriseInfoDomainMapper.selectByPrimaryKey(enterpriseId);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(enterpriseInfoDomain);
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
