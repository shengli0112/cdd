package com.cdd.gsl.controller;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.*;
import com.cdd.gsl.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;




    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);



    @RequestMapping("companyData")
    public CommonResult companyData(StatisticsContionVo statisticsContionVo){
        logger.info("StatisticsController companyData statisticsContionVo-{}",statisticsContionVo.toString());
        CommonResult commonResult = statisticsService.companyData(statisticsContionVo);
        return commonResult;
    }

    @RequestMapping("companyUser")
    public CommonResult companyUser(String companyName){
        logger.info("StatisticsController companyUser companyName-{}",companyName);
        CommonResult commonResult = statisticsService.companyUser(companyName);
        return commonResult;
    }


}
