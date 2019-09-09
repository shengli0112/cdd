package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.EntrustAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.EntrustInfoDomain;
import com.cdd.gsl.service.EntrustService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("entrust")
public class AdminEntrustController {
    private Logger logger = LoggerFactory.getLogger(AdminEntrustController.class);
    @Autowired
    private EntrustService entrustService;

    @RequestMapping("/findAdminEntrustList")
    public CommonResult findAdminEntrustList(EntrustAdminConditionVo entrustAdminConditionVo){
        return entrustService.findAdminEntrustList(entrustAdminConditionVo);
    }

    @RequestMapping("/deleteEntrust")
    public CommonResult deleteEntrust(@RequestBody JSONObject jsonObject){
        CommonResult commonResult = new CommonResult();
        if(jsonObject != null){
            EntrustInfoDomain entrustInfoDomain = new EntrustInfoDomain();
            entrustInfoDomain.setId(jsonObject.getLong("entrustId"));
            entrustInfoDomain.setStatus(0);
            commonResult = entrustService.deleteEntrust(entrustInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @RequestMapping("/recoverEntrust")
    public CommonResult recoverEntrust(@RequestBody JSONObject jsonObject){
        CommonResult commonResult = new CommonResult();
        if(jsonObject != null){
            EntrustInfoDomain entrustInfoDomain = new EntrustInfoDomain();
            entrustInfoDomain.setId(jsonObject.getLong("entrustId"));
            entrustInfoDomain.setStatus(1);
            commonResult = entrustService.updateEntrust(entrustInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    @RequestMapping("/updateEntrust")
    public CommonResult updateEntrust(@RequestBody EntrustInfoDomain entrustInfoDomain){
        CommonResult commonResult = new CommonResult();
        if(entrustInfoDomain != null){

            commonResult = entrustService.updateEntrust(entrustInfoDomain);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数为空");
        }
        return commonResult;
    }

    /**
     * 导出委托信息
     */
    @RequestMapping("exportEntrustList")
    public CommonResult exportEntrustList(String keyword){
        CommonResult commonResult = entrustService.exportEntrustList(keyword);

        return commonResult;
    }
}
