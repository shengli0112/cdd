package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.StorageInfoDomainMapper;
import com.cdd.gsl.domain.StorageInfoDomain;
import com.cdd.gsl.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageInfoDomainMapper storageInfoDomainMapper;

    @Override
    public CommonResult createStorage(StorageInfoDomain storageInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if(storageInfoDomain != null){
            storageInfoDomainMapper.insertSelective(storageInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("添加成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }
}
