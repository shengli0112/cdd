package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;

import java.util.List;

public interface RegionService {

    public CommonResult<List<String>> findAllCity();

    public CommonResult findCountyByCity(String city);
}
