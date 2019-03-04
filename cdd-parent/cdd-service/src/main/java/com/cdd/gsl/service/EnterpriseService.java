package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.vo.EnterpriseConditionVo;

import java.util.List;

public interface EnterpriseService {
    public CommonResult createEnterprise(EnterpriseInfoDomain enterpriseInfoDomain);

    public CommonResult<List<EnterpriseInfoDomain>> findEnterpriseInfoList(EnterpriseConditionVo enterpriseConditionVo);
}