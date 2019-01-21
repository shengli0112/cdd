package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("region")
public class RegionController {

    /**
     * 根据城市获取对应的 县区以及县区下面的区域
     * @param city
     * @return
     */
    @RequestMapping("findRegionByCity")
    public CommonResult findRegionByCity(String city){
        return null;
    }
}
