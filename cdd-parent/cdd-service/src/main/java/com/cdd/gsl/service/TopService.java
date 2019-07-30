package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.vo.HouseTopParamVo;

public interface TopService {
    CommonResult topAction(HouseTopParamVo houseTopParamVo);

    CommonResult topList();
}
