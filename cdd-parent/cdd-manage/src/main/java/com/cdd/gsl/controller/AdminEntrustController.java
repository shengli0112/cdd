package com.cdd.gsl.controller;

import com.cdd.gsl.admin.EntrustAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.EntrustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("entrust")
public class AdminEntrustController {
    @Autowired
    private EntrustService entrustService;

    public CommonResult findEntrustInfoList(EntrustAdminConditionVo entrustAdminConditionVo){
        return null;
    }
}
