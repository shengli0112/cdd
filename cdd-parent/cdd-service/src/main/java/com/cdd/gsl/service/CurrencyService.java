package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.vo.ConsumeRecordConditionVo;
import com.cdd.gsl.vo.ConsumeRecordVo;

public interface CurrencyService {
    public CommonResult currencyList();

    public CommonResult isPublish(Long userId);

    public CommonResult integralCount(Long userId);

    public CommonResult consumeRecord(ConsumeRecordConditionVo consumeRecordConditionVo);
}
