package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.SlideInfoDao;
import com.cdd.gsl.dao.SlideInfoDomainMapper;
import com.cdd.gsl.domain.SlideInfoDomain;
import com.cdd.gsl.service.SlideService;
import com.cdd.gsl.vo.SlideConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideInfoDomainMapper slideInfoDomainMapper;

    @Autowired
    private SlideInfoDao slideInfoDao;

    @Override
    public CommonResult addSlide(SlideInfoDomain slideInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(slideInfoDomain != null){
            slideInfoDomainMapper.insertSelective(slideInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult slideList(SlideConditionVo slideConditionVo) {
        CommonResult commonResult = new CommonResult();
        int count = slideInfoDao.slideCount(slideConditionVo);
        List<SlideInfoDomain> slideInfoDomainList = slideInfoDao.slideList(slideConditionVo);
        JSONObject json = new JSONObject();
        json.put("total",count);
        json.put("slideList",slideInfoDomainList);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(json);
        return commonResult;
    }

    @Override
    public CommonResult deleteSlide(JSONObject json) {
        CommonResult commonResult = new CommonResult();
        if(json != null){
            SlideInfoDomain slideInfoDomain = new SlideInfoDomain();
            slideInfoDomain.setId(json.getLong("slideId"));
            slideInfoDomain.setStatus(0);
            slideInfoDomainMapper.updateByPrimaryKeySelective(slideInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult recoverSlide(JSONObject json) {
        CommonResult commonResult = new CommonResult();
        if(json != null){
            SlideInfoDomain slideInfoDomain = new SlideInfoDomain();
            slideInfoDomain.setId(json.getLong("slideId"));
            slideInfoDomain.setStatus(1);
            slideInfoDomainMapper.updateByPrimaryKeySelective(slideInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult orderSlide(JSONObject json) {
        CommonResult commonResult = new CommonResult();
        if(json != null){
            SlideInfoDomain slideInfoDomain = new SlideInfoDomain();
            slideInfoDomain.setId(json.getLong("slideId"));
            slideInfoDomain.setSequence(json.getInteger("sequence"));
            slideInfoDomainMapper.updateByPrimaryKeySelective(slideInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("排序成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }
        return commonResult;
    }


}
