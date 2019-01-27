package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.CommonDictDomain;
import com.cdd.gsl.domain.FollowInfoDomain;
import com.cdd.gsl.domain.ThirdUserInfoDomain;
import com.cdd.gsl.vo.FollowInfoVo;
import com.cdd.gsl.vo.LoginTokenVo;
import com.cdd.gsl.vo.LoginUserVo;
import com.cdd.gsl.vo.UserInfoVo;

import java.util.List;

public interface UserService {
    CommonResult thirdLogin(ThirdUserInfoDomain thirdUserInfoDomain);

    CommonResult register(UserInfoVo userInfoVo);

    CommonResult checkPhone(String phone);

    CommonResult forgetPassword(UserInfoVo userInfoVo);

    CommonResult<LoginTokenVo> login(LoginUserVo loginUserVo);

    CommonResult agreeCompany(Long userId,Long companyId);

    CommonResult bindPhone(Long userId,String phone);

    CommonResult followInfo(FollowInfoDomain followInfoDomain);

    CommonResult cancelFollow(FollowInfoVo followInfoVo);

    List<CommonDictDomain> findDictInfo(String dictName);

    CommonResult authenticationBroker(ApplyBrokerInfoDomain applyBrokerInfoDomain);
}
