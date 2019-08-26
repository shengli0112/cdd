package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ScreenAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.OpenScreenInfoDomainMapper;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.OpenScreenInfoDomain;
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
        logger.info("SlideController slideList start");
        return slideService.slideList(slideConditionVo);
    }

    @RequestMapping(value = "/deleteSlide")
    public CommonResult deleteSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController deleteSlide start");
        return slideService.deleteSlide(json);
    }

    @RequestMapping(value = "/recoverSlide")
    public CommonResult recoverSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController recoverSlide start");
        return slideService.recoverSlide(json);
    }

    @RequestMapping(value = "/orderSlide")
    public CommonResult orderSlide(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController orderSlide start");
        return slideService.orderSlide(json);
    }

    @RequestMapping(value = "/addSlide")
    public CommonResult addSlide(@RequestBody SlideInfoDomain slideInfoDomain) throws Exception {
        logger.info("SlideController addSlide start");
        return slideService.addSlide(slideInfoDomain);
    }

    @RequestMapping(value = "/updateSlide")
    public CommonResult updateSlide(@RequestBody SlideInfoDomain slideInfoDomain) throws Exception {
        logger.info("SlideController updateSlide start");
        return slideService.updateSlide(slideInfoDomain);
    }

    @RequestMapping(value = "/findSlidePosition")
    public CommonResult findSlidePosition() throws Exception {
        logger.info("SlideController findSlidePosition start");
        return slideService.findSlidePosition();
    }

    @RequestMapping(value = "/openScreenList")
    public CommonResult openScreenList(ScreenAdminConditionVo screenAdminConditionVo) throws Exception {
        logger.info("SlideController openScreenList start");
        return slideService.openScreenList(screenAdminConditionVo);
    }

    @RequestMapping(value = "/addOpenScreen")
    public CommonResult addOpenScreen(@RequestBody OpenScreenInfoDomain openScreenInfoDomain) throws Exception {
        logger.info("SlideController addOpenScreen start");
        return slideService.addOpenScreen(openScreenInfoDomain);
    }

    @RequestMapping(value = "/deleteOpenScreen")
    public CommonResult deleteOpenScreen(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController deleteOpenScreen start");
        CommonResult commonResult = new CommonResult();
        if(json != null){
            Long openScreenId = json.getLong("openScreenId");
            OpenScreenInfoDomain openScreenInfoDomain = new OpenScreenInfoDomain();
            openScreenInfoDomain.setId(openScreenId);
            openScreenInfoDomain.setStatus(0);
            commonResult = slideService.updateOpenScreen(openScreenInfoDomain);
        }else {
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setData("参数不能为空");
        }
        return commonResult;
    }

    @RequestMapping(value = "/recoverOpenScreen")
    public CommonResult recoverOpenScreen(@RequestBody JSONObject json) throws Exception {
        logger.info("SlideController recoverOpenScreen start");
        CommonResult commonResult = new CommonResult();
        if(json != null){
            Long openScreenId = json.getLong("openScreenId");
            OpenScreenInfoDomain openScreenInfoDomain = new OpenScreenInfoDomain();
            openScreenInfoDomain.setId(openScreenId);
            openScreenInfoDomain.setStatus(1);
            commonResult = slideService.updateOpenScreen(openScreenInfoDomain);
        }else {
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setData("参数不能为空");
        }
        return commonResult;
    }

    @RequestMapping(value = "/updateOpenScreen")
    public CommonResult updateOpenScreen(@RequestBody OpenScreenInfoDomain openScreenInfoDomain) throws Exception {
        logger.info("SlideController deleteOpenScreen start");
        CommonResult commonResult = new CommonResult();
        if(openScreenInfoDomain != null){
            commonResult = slideService.updateOpenScreen(openScreenInfoDomain);
        }else {
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setData("参数不能为空");
        }
        return commonResult;
    }

}
