package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {

    @Autowired
    private RegionService regionService;
    /**
     * 根据城市获取对应的 县区以及县区下面的区域
     * @param city
     * @return
     */
    @RequestMapping("findCountyByCity")
    public CommonResult<List<String>> findRegionByCity(String city){
        CommonResult<List<String>> commonResult = regionService.findCountyByCity(city);
        return commonResult;
    }

    @RequestMapping("findTownByCounty")
    public CommonResult<List<String>> findTownByCounty(String county){
        CommonResult<List<String>> commonResult = regionService.findTownByCounty(county);
        return commonResult;
    }

    @RequestMapping("findAllCity")
    public CommonResult<List<String>> findAllCity(){
        CommonResult<List<String>> commonResult = regionService.findAllCity();
        return commonResult;
    }

    @RequestMapping("findFirstCodeCity")
    public CommonResult<List<String>> findFirstCodeCity(Long userId){
        CommonResult<List<String>> commonResult = regionService.findFirstCodeCity(userId);
        return commonResult;
    }
}
