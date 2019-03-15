package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.TrailInfoDomainMapper;
import com.cdd.gsl.domain.TrailInfoDomain;
import com.cdd.gsl.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrailServiceImpl implements TrailService {
    @Autowired
    private TrailInfoDomainMapper trailInfoDomainMapper;
    @Override
    public CommonResult addTrail(TrailInfoDomain trailInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(trailInfoDomain != null){
            trailInfoDomain.setCreateTs(new Date());
            trailInfoDomainMapper.insertSelective(trailInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不正确");
        }
        return commonResult;
    }

    @Override
    public CommonResult findTrailList(Long houseId) {
        CommonResult commonResult = new CommonResult();
        if(houseId != null){}
        return null;
    }
}
