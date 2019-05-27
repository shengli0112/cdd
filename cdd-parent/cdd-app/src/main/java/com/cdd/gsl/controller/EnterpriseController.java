package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EnterpriseInfoDomain;
import com.cdd.gsl.service.EnterpriseService;
import com.cdd.gsl.vo.EnterpriseConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 创建企业圈
     * @param enterpriseInfoDomain
     * @return
     */
    @RequestMapping("createEnterprise")
    public CommonResult createEnterprise(@RequestBody EnterpriseInfoDomain enterpriseInfoDomain){
        CommonResult commonResult = enterpriseService.createEnterprise(enterpriseInfoDomain);
        return commonResult;
    }

    /**
     * 更新企业圈
     * @param enterpriseInfoDomain
     * @return
     */
    @RequestMapping("updateEnterprise")
    public CommonResult updateEnterprise(@RequestBody EnterpriseInfoDomain enterpriseInfoDomain){
        CommonResult commonResult = enterpriseService.updateEnterprise(enterpriseInfoDomain);
        return commonResult;
    }

    /**
     * 删除企业
     * @return
     */
    @RequestMapping("deleteEnterprise")
    public CommonResult deleteEnterprise(Long enterpriseId,Long userId){
        CommonResult commonResult = enterpriseService.deleteEnterprise(enterpriseId,userId);
        return commonResult;
    }

    @RequestMapping("findEnterpriseInfoList")
    public CommonResult findEnterpriseInfoList(EnterpriseConditionVo enterpriseConditionVo){
        CommonResult<List<EnterpriseInfoDomain>> commonResult = enterpriseService.findEnterpriseInfoList(enterpriseConditionVo);
        return commonResult;
    }

    @RequestMapping("findEnterpriseDetail")
    public CommonResult findEnterpriseDetail(Long enterpriseId){
        CommonResult commonResult = enterpriseService.findEnterpriseDetail(enterpriseId);
        return commonResult;
    }
}
