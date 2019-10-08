package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class AdminStatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/monthStatistics")
    public CommonResult monthStatistics(){
        return statisticsService.monthStatistics();
    }
}
