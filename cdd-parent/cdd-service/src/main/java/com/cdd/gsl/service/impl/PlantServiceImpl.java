package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.PlantInfoDomainMapper;
import com.cdd.gsl.domain.PlantInfoDomain;
import com.cdd.gsl.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantInfoDomainMapper plantInfoDomainMapper;

    @Override
    public CommonResult createPlant(PlantInfoDomain plantInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(plantInfoDomain != null){
            plantInfoDomainMapper.insertSelective(plantInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return  commonResult;
    }
}
