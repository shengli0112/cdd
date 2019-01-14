package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.LandInfoDomainMapper;
import com.cdd.gsl.domain.LandInfoDomain;
import com.cdd.gsl.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandServiceImpl implements LandService {

    @Autowired
    private LandInfoDomainMapper landInfoDomainMapper;

    @Override
    public CommonResult createLand(LandInfoDomain landInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if (landInfoDomain != null){
            landInfoDomainMapper.insertSelective(landInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("创建失败");
        }

        return commonResult;
    }
}
