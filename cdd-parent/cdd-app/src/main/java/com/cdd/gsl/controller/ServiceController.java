package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ServiceInfoDomain;
import com.cdd.gsl.service.ServiceInfoService;
import com.cdd.gsl.service.ServiceTypeService;
import com.cdd.gsl.vo.ServiceInfoConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class ServiceController {

    @Autowired
    private ServiceInfoService serviceInfoService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @RequestMapping("/findServiceType")
    public CommonResult findServiceType(){
        return serviceTypeService.findServiceType();
    }

    @RequestMapping("/createServiceInfo")
    public CommonResult createServiceInfo(@RequestBody ServiceInfoDomain serviceInfoDomain){
        return serviceInfoService.createServiceInfo(serviceInfoDomain);
    }

    @RequestMapping("/updateServiceInfo")
    public CommonResult updateServiceInfo(ServiceInfoDomain serviceInfoDomain){
        return serviceInfoService.updateServiceInfo(serviceInfoDomain);
    }

    @RequestMapping("/deleteServiceInfo")
    public CommonResult deleteServiceInfo(Long userId,Long serviceId){
        return serviceInfoService.deleteServiceInfo(userId,serviceId);
    }

    @RequestMapping("/findServiceInfoList")
    public CommonResult findServiceInfoList(ServiceInfoConditionVo serviceInfoConditionVo){
        return serviceInfoService.findServiceInfoList(serviceInfoConditionVo);
    }

    @RequestMapping("/findServiceInfoListByUserId")
    public CommonResult findServiceInfoListByUserId(ServiceInfoConditionVo serviceInfoConditionVo){
        return serviceInfoService.findServiceInfoListByUserId(serviceInfoConditionVo);
    }

    @RequestMapping("checkPhone")
    public CommonResult checkPhone(Long userId,Long serviceId){
        return serviceInfoService.checkPhone(userId,serviceId);
    }


}
