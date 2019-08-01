package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.CurrencyService;
import com.cdd.gsl.vo.ConsumeRecordConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("currencyList")
    public CommonResult currencyList(){
        return currencyService.currencyList();
    }

    /**
     * 是否可以发布
     * @return
     */
    @RequestMapping("isPublish")
    public CommonResult isPublish(Long userId){
        return currencyService.isPublish(userId);
    }

    /**
     * 积分数
     */
    @RequestMapping("integralCount")
    public CommonResult integralCount(Long userId){
        return currencyService.integralCount(userId);
    }

    /**
     * 消费记录
     */
    @RequestMapping("consumeRecord")
    public CommonResult consumeRecord(ConsumeRecordConditionVo consumeRecordConditionVo){
        return currencyService.consumeRecord(consumeRecordConditionVo);
    }
}
