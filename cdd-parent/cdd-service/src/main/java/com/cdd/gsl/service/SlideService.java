package com.cdd.gsl.service;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.vo.SlideConditionVo;

public interface SlideService {
    public CommonResult addSlide(SlideInfoDomain slideInfoDomain);

    public CommonResult slideList(SlideConditionVo slideConditionVo);

    public CommonResult deleteSlide(JSONObject json);

    public CommonResult recoverSlide(JSONObject json);

    public CommonResult orderSlide(JSONObject json);
}
