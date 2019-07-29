package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.InformHouseRecordDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.vo.AdminInformInfoConditionVo;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("inform")
public class InformController {
    private Logger logger = LoggerFactory.getLogger(InformController.class);
    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/findInformList")
    public CommonResult findInformList(AdminInformInfoConditionVo adminInformInfoConditionVo) throws Exception {
        logger.info("InformController findInformList start");
        return adminService.findInformList(adminInformInfoConditionVo);
    }

    @RequestMapping(value = "/passAudit")
    public CommonResult passAudit(@RequestBody ApplyBrokerInfoDomain applyBrokerInfoDomain) throws Exception {
        logger.info("BrokerController brokerList start");
        return adminService.passAudit(applyBrokerInfoDomain);
    }

    @RequestMapping(value = "/handlerInform")
    public CommonResult handlerInform(@RequestBody JSONObject json) throws Exception {
        logger.info("InformController handlerInform start");
        CommonResult commonResult = new CommonResult();
        if(json != null){
            InformHouseRecordDomain informHouseRecordDomain = new InformHouseRecordDomain();
            informHouseRecordDomain.setId(json.getLong("informId"));
            informHouseRecordDomain.setStatus(1);
//            informHouseRecordDomain.setUpdateTs(new Date());
            adminService.handlerInform(informHouseRecordDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("处理成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("处理失败，参数不正确");
        }
        return commonResult;
    }


}
