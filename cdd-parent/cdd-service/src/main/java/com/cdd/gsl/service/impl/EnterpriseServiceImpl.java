package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.EnterpriseInfoDao;
import com.cdd.gsl.dao.EnterpriseInfoDomainMapper;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.vo.EnterpriseConditionVo;
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
    public CommonResult<List<EnterpriseInfoDomain>> findEnterpriseInfoList(EnterpriseConditionVo enterpriseConditionVo) {
        CommonResult<List<EnterpriseInfoDomain>> commonResult = new CommonResult<>();
        List<EnterpriseInfoDomain> enterpriseInfoDomainList = enterpriseInfoDao.selectEnterpriseInfoListByCondition(enterpriseConditionVo);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(enterpriseInfoDomainList);
        return commonResult;
    }
}