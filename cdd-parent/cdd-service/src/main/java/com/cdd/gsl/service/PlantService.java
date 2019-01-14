package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.PlantInfoDomain;

public interface PlantService {

    public CommonResult createPlant(PlantInfoDomain plantInfoDomain);
}
