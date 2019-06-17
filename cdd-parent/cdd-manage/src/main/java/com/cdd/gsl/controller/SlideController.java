package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.SlideService;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.SlideConditionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slide")
public class SlideController {
    private Logger logger = LoggerFactory.getLogger(SlideController.class);
    @Autowired
    private SlideService slideService;


    @RequestMapping(value = "/slideList")
    public CommonResult slideList(SlideConditionVo slideConditionVo) throws Exception {
        logger.info("SlideController brokerList start");
        return slideService.slideList(slideConditionVo);
    }

    @RequestMapping(value = "/deleteSlide")
    public CommonResult deleteSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController brokerList start");
        return slideService.deleteSlide(json);
    }

    @RequestMapping(value = "/recoverSlide")
    public CommonResult recoverSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController brokerList start");
        return slideService.recoverSlide(json);
    }

    @RequestMapping(value = "/orderSlide")
    public CommonResult orderSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController brokerList start");
        return slideService.orderSlide(json);
    }

    @RequestMapping(value = "/addSlide")
    public CommonResult addSlide(SlideInfoDomain slideInfoDomain) throws Exception {
        logger.info("SlideController brokerList start");
        return slideService.addSlide(slideInfoDomain);
    }

}
