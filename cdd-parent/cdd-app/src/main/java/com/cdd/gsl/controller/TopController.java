package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.service.TopService;
import com.cdd.gsl.vo.HouseTopParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("top")
public class TopController {


    @Autowired
    private TopService topService;

    @RequestMapping("topAction")
    public CommonResult topAction(@RequestBody HouseTopParamVo houseTopParamVo){
        return topService.topAction(houseTopParamVo);
    }

    @RequestMapping("topList")
    public CommonResult topList(){
        return topService.topList();
    }
}
