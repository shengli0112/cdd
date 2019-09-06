package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.vo.StatisticsContionVo;

public interface StatisticsService {
    CommonResult companyData(StatisticsContionVo statisticsContionVo);

    CommonResult companyUser(String companyName);
}
