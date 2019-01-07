package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.FollowInfoDomain;
import com.cdd.gsl.domain.ThirdUserInfoDomain;
import com.cdd.gsl.vo.FollowInfoVo;
import com.cdd.gsl.vo.LoginUserVo;
import com.cdd.gsl.vo.UserInfoVo;

public interface UserService {
    CommonResult thirdLogin(ThirdUserInfoDomain thirdUserInfoDomain);

    CommonResult register(UserInfoVo userInfoVo);

    CommonResult checkPhone(String phone);

    CommonResult forgetPassword(UserInfoVo userInfoVo);

    CommonResult login(LoginUserVo loginUserVo);

    CommonResult agreeCompany(Long userId,Long companyId);

    CommonResult bindPhone(Long userId,String phone);

    CommonResult followInfo(FollowInfoDomain followInfoDomain);

    CommonResult cancelFollow(FollowInfoVo followInfoVo);
}
