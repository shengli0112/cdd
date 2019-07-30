package com.cdd.gsl.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.vo.AdminVo;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.ValidateLoginVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("broker")
public class BrokerController {
    private Logger logger = LoggerFactory.getLogger(BrokerController.class);
    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/brokerList")
    public CommonResult brokerList(ApplyBrokerConditionVo applyBrokerConditionVo) throws Exception {
        logger.info("BrokerController brokerList start");
        return adminService.brokerList(applyBrokerConditionVo);
    }

    @RequestMapping(value = "/companyBrokerList")
    public CommonResult companyBrokerList(String companyName) throws Exception {
        logger.info("BrokerController companyBrokerList start");
        return adminService.companyBrokerList(companyName);
    }

    @RequestMapping(value = "/passAudit")
    public CommonResult passAudit(@RequestBody ApplyBrokerInfoDomain applyBrokerInfoDomain) throws Exception {
        logger.info("BrokerController brokerList start");
        return adminService.passAudit(applyBrokerInfoDomain);
    }

    @RequestMapping(value = "/companyUserList")
    public CommonResult companyUserList(String companyName) throws Exception {
        logger.info("BrokerController companyBrokerList start");
        return adminService.companyUserList(companyName);
    }



}
