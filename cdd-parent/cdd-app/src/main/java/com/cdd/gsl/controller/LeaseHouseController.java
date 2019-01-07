package com.cdd.gsl.controller;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.LeaseHouseInfoDomain;
import com.cdd.gsl.service.LeaseHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  出租房屋接口
 *  created：2018/12/28
 */
@RestController
@RequestMapping("lease")
public class LeaseHouseController {
    @Autowired
    private LeaseHouseService leaseHouseService;

    @RequestMapping("addLeaseHouse")
    public CommonResult addLeaseHouse(LeaseHouseInfoDomain houseInfoDomain){
        CommonResult commonResult = new CommonResult();
        leaseHouseService.addLeaseHouse(houseInfoDomain);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("新增出租房屋信息成功");
        return commonResult;
    }

    @RequestMapping("updateLeaseHouse")
    public CommonResult updateLeaseHouse(LeaseHouseInfoDomain houseInfoDomain){
        CommonResult commonResult = new CommonResult();
        leaseHouseService.updateLeaseHouse(houseInfoDomain);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("更新出租房屋信息成功");
        return commonResult;
    }

    @RequestMapping("deleteLeaseHouse")
    public CommonResult deleteLeaseHouse(Long leaseHouseId){
        CommonResult commonResult = new CommonResult();
        if(leaseHouseId != null){
            LeaseHouseInfoDomain leaseHouseInfoDomain = new LeaseHouseInfoDomain();
            leaseHouseInfoDomain.setId(leaseHouseId);
            leaseHouseInfoDomain.setStatus(0);
            leaseHouseService.updateLeaseHouse(leaseHouseInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("删除房源成功");
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }

        return commonResult;
    }

}
