package com.cdd.gsl.service;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ScreenAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.OpenScreenInfoDomain;
import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.vo.SlideConditionVo;
import com.cdd.gsl.vo.SlideParamVo;

public interface SlideService {
    public CommonResult addSlide(SlideInfoDomain slideInfoDomain);

    public CommonResult updateSlide(SlideInfoDomain slideInfoDomain);

    public CommonResult slideList(SlideConditionVo slideConditionVo);

    public CommonResult deleteSlide(JSONObject json);

    public CommonResult recoverSlide(JSONObject json);

    public CommonResult orderSlide(JSONObject json);

    public CommonResult findSlideList(SlideParamVo slideParamVo);

    public CommonResult findSlidePosition();

    public CommonResult addOpenScreen(OpenScreenInfoDomain openScreenInfoDomain);

    public CommonResult updateOpenScreen(OpenScreenInfoDomain openScreenInfoDomain);

    public CommonResult openScreenList(ScreenAdminConditionVo screenAdminConditionVo);

    public CommonResult slidePositionList();

    public CommonResult findScreenList();
}
