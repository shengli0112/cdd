package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.SlideService;
import com.cdd.gsl.service.TopService;
import com.cdd.gsl.vo.HouseTopParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slide")
public class SlideController {


    @Autowired
    private SlideService slideService;


    @RequestMapping("findSlideList")
    public CommonResult findSlideList(String city){
        return slideService.findSlideList(city);
    }
}
