package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.FollowInfoDomain;
import com.cdd.gsl.domain.ThirdUserInfoDomain;
import com.cdd.gsl.service.CompanyService;
import com.cdd.gsl.service.UserService;
import com.cdd.gsl.vo.*;
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

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("register")
    public CommonResult register(@RequestBody UserInfoVo userParamVo){
        CommonResult commonResult = userService.register(userParamVo);
        return commonResult;
    }

    @RequestMapping("login")
    public CommonResult login(LoginUserVo loginUserVo){
        return null;

    }

    /**
     * 三方登录
     * @param thirdUserInfoDomain
     * @return
     */
    @RequestMapping("thirdLogin")
    public CommonResult thirdLogin(@RequestBody ThirdUserInfoDomain thirdUserInfoDomain){
        logger.info("UserController thirdLogin ThirdUserVo -{}",thirdUserInfoDomain);
        CommonResult commonResult = userService.thirdLogin(thirdUserInfoDomain);
        return commonResult;
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

    @RequestMapping("forgetPassword")
    public CommonResult forgetPassword(UserInfoVo forgetPasswordVo){
        CommonResult result = userService.forgetPassword(forgetPasswordVo);
        return result;
    }

    @RequestMapping("checkPhone")
    public CommonResult checkPhone(String phone){
        CommonResult result = userService.checkPhone(phone);
        return result;
    }

    /**
     * 同意加入公司
     */
    @RequestMapping("agreeCompany")
    public CommonResult agreeCompany(Long companyId){
        CommonResult commonResult = new CommonResult();
        Long userId = 0L;
        userService.agreeCompany(userId,companyId);

        return commonResult;
    }

    @RequestMapping("bindPhone")
    public CommonResult bindPhone(String phone){
        Long userId = 0L;
        CommonResult commonResult = userService.bindPhone(userId,phone);
        return commonResult;
    }

    /**
     * 个人托管厂房创建
     */
    @RequestMapping("addPlant")
    public CommonResult addPlant(){
        return null;
    }

    /**
     * 个人托管仓库创建
     */
    @RequestMapping("addStorage")
    public CommonResult addStorage(){
        return null;
    }

    /**
     * 个人托管土地创建
     */
    @RequestMapping("addLand")
    public CommonResult addLand(){
        return null;
    }

    /**
     * 关注信息
     */
    @RequestMapping("followInfo")
    public CommonResult followInfo(FollowInfoDomain followInfoDomain){
        CommonResult commonResult = userService.followInfo(followInfoDomain);
        return commonResult;
    }

    /**
     * 取消关注信息
     */
    @RequestMapping("cancelFollow")
    public CommonResult cancelFollow(FollowInfoVo followInfoVo){
        CommonResult commonResult = userService.cancelFollow(followInfoVo);
        return commonResult;
    }
}
