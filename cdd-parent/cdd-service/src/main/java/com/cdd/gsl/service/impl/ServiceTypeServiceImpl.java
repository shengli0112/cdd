package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.ServiceTypeDao;
import com.cdd.gsl.service.ServiceTypeService;
import com.cdd.gsl.vo.ServiceTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    private ServiceTypeDao serviceTypeDao;

    @Override
    public CommonResult findServiceType() {
        CommonResult commonResult = new CommonResult();
        List<ServiceTypeVo> serviceTypeVoList = serviceTypeDao.getServiceTypeList();
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(serviceTypeVoList);
        return commonResult;
    }
}
