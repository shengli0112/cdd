package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.ServiceAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.service.ServiceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class AdminServiceController {
    private Logger logger = LoggerFactory.getLogger(AdminEntrustController.class);

    @Autowired
    private ServiceInfoService serviceInfoService;

    @RequestMapping("/findAdminServiceList")
    public CommonResult findAdminServiceList(ServiceAdminConditionVo serviceAdminConditionVo){
        return serviceInfoService.findAdminServiceInfoList(serviceAdminConditionVo);
    }

    @RequestMapping("/updateServiceInfo")
    public CommonResult updateServiceInfo(@RequestBody ServiceInfoDomain serviceInfoDomain){
        return serviceInfoService.updateServiceInfo(serviceInfoDomain);
    }

    @RequestMapping("/deleteServiceInfo")
    public CommonResult deleteServiceInfo(@RequestBody JSONObject jsonObject){
        CommonResult commonResult = new CommonResult();
        if(jsonObject != null){
            ServiceInfoDomain serviceInfoDomain = new ServiceInfoDomain();
            serviceInfoDomain.setId(jsonObject.getLong("serviceId"));
            serviceInfoDomain.setStatus(0);
            commonResult = serviceInfoService.updateServiceInfo(serviceInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }

        return commonResult;
    }
}
