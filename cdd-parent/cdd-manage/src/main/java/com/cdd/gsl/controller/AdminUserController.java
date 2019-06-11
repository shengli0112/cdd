package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.UserInfoDomainMapper;
import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.service.UserService;
import com.cdd.gsl.vo.UserConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AdminUserController {

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * @return
     */
    @RequestMapping("/userList")
    public CommonResult userList(UserConditionVo userConditionVo){
        return userService.userList(userConditionVo);
    }

    /**
     * 删除用户
     */

    @RequestMapping("/deleteUser")
    public CommonResult deleteUser(Long userId){
        return userService.deleteUser(userId);
    }

    /**
     * 恢复用户
     */
    @RequestMapping("/recoverUser")
    public CommonResult recoverUser(Long userId){
        return userService.recoverUser(userId);
    }

    /**
     * 添加用户
     */
    @RequestMapping("/addUser")
    public CommonResult addUser(@RequestBody UserInfoDomain userInfoDomain){
        return userService.addUser(userInfoDomain);
    }

    /**
     * 充币
     */
    @RequestMapping("/updateCurrency")
    public CommonResult updateCurrency(@RequestBody UserInfoDomain userInfoDomain){

        return userService.updateCurrency(userInfoDomain);
    }
}
