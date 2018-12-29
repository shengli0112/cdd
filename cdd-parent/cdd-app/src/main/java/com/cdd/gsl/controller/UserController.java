package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.CompanyService;
import com.cdd.gsl.vo.CompanyVo;
import com.cdd.gsl.vo.ThirdUserVo;
import com.cdd.gsl.vo.UserCompanyInfoVo;
import com.cdd.gsl.vo.UserParamVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private CompanyService companyService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("register")
    public CommonResult register(@RequestBody UserParamVo userParamVo){
        return null;
    }

    @RequestMapping("login")
    public CommonResult login(){
        return null;
    }

    /**
     * 三方登录
     * @param thirdUserVo
     * @return
     */
    @RequestMapping("thirdLogin")
    public CommonResult thirdLogin(@RequestBody ThirdUserVo thirdUserVo){
        logger.info("UserController thirdLogin ThirdUserVo -{}",thirdUserVo);
        return null;
    }

    /**
     * 创建公司
     * @param companyVo
     * @return
     */
    @RequestMapping("createCompany")
    public CommonResult createCompany(@RequestBody CompanyVo companyVo){
        logger.info("UserController createCompany ThirdUserVo -{}",companyVo);
        CommonResult result = companyService.createCompany(1l,companyVo);
        return result;
    }

    /**
     * 邀请员工加入公司
     * @param companyId
     * @param userId
     * @return
     */
    @RequestMapping("inviteUserToCompany")
    public CommonResult inviteUserToCompany(Long companyId,Long userId){
        logger.info("UserController inviteUserToCompany companyId -{}，userId - {}",companyId,userId);
        CommonResult result = companyService.inviteUserToCompany(companyId,userId);
        return result;
    }

    @RequestMapping("findUserByPhone")
    public CommonResult<UserCompanyInfoVo> findUserByPhone(Long companyId,String phone){
        CommonResult<UserCompanyInfoVo> result = companyService.findUserByPhone(companyId,phone);
        return result;
    }

}
