package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.LandInfoDomain;

public interface LandService {

    public CommonResult createLand(LandInfoDomain landInfoDomain);

}
