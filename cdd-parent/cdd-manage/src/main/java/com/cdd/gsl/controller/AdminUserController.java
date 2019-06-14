package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.UserInfoDomainMapper;
import com.cdd.gsl.service.UserService;
import com.cdd.gsl.vo.UserConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class AdminUserController {

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

}
