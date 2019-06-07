package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;

public interface CurrencyService {
    public CommonResult currencyList();

    public CommonResult isPublish(Long userId);
}
