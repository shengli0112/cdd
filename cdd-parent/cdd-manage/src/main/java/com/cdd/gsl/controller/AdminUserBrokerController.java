package com.cdd.gsl.controller;

import com.cdd.gsl.admin.UserBrokerConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.ExcelUtil;
import com.cdd.gsl.domain.UserBrokerInfoDomain;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.service.UserBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("userBroker")
public class AdminUserBrokerController {

    private Logger logger = LoggerFactory.getLogger(AdminUserBrokerController.class);

    @Autowired
    private UserBrokerService userBrokerService;

    @RequestMapping("importUserBroker")
    public CommonResult importUserBroker(HttpServletRequest request, HttpServletResponse response){

        CommonResult commonResult = new CommonResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("file");

        if(file.isEmpty()){
            try {
                commonResult.setFlag(0);
                commonResult.setMessage("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
                commonResult.setFlag(0);
                commonResult.setMessage("服务器导入异常");
            }
        }

        commonResult = userBrokerService.importUserBroker(file);

        return commonResult;


    }

    @RequestMapping("findUserBroker")
    public CommonResult findUserBroker(UserBrokerConditionVo userBrokerConditionVo){
        logger.info("AdminUserBrokerController findUserBroker -{}",userBrokerConditionVo.getKeyword());
        CommonResult commonResult = new CommonResult();
        try{
            commonResult = userBrokerService.findUserBroker(userBrokerConditionVo);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @RequestMapping("updateUserBroker")
    public CommonResult updateUserBroker(@RequestBody UserBrokerInfoDomain userBrokerInfoDomain){
        logger.info("AdminUserBrokerController updateUserBroker -{}",userBrokerInfoDomain.getPhone());
        CommonResult commonResult = new CommonResult();
        try{
            commonResult = userBrokerService.updateUserBroker(userBrokerInfoDomain);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @RequestMapping("deleteUserBroker")
    public CommonResult deleteUserBroker(Long userBrokerId){
        logger.info("AdminUserBrokerController updateUserBroker -{}",userBrokerId);
        CommonResult commonResult = new CommonResult();
        try{
            UserBrokerInfoDomain userBrokerInfoDomain = new UserBrokerInfoDomain();
            userBrokerInfoDomain.setId(userBrokerId);
            userBrokerInfoDomain.setStatus(0);
            commonResult = userBrokerService.updateUserBroker(userBrokerInfoDomain);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @RequestMapping("recoverUserBroker")
    public CommonResult recoverUserBroker(Long userBrokerId){
        logger.info("AdminUserBrokerController updateUserBroker -{}",userBrokerId);
        CommonResult commonResult = new CommonResult();
        try{
            UserBrokerInfoDomain userBrokerInfoDomain = new UserBrokerInfoDomain();
            userBrokerInfoDomain.setId(userBrokerId);
            userBrokerInfoDomain.setStatus(1);
            commonResult = userBrokerService.updateUserBroker(userBrokerInfoDomain);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @RequestMapping("addUserBroker")
    public CommonResult addUserBroker(UserBrokerInfoDomain userBrokerInfoDomain){
        logger.info("AdminUserBrokerController updateUserBroker -{}",userBrokerInfoDomain.getPhone());
        CommonResult commonResult = new CommonResult();
        try{
            commonResult = userBrokerService.addUserBroker(userBrokerInfoDomain);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setFlag(0);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }


}
