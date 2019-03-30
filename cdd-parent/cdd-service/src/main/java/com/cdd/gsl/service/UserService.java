package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.CommonDictDomain;
import com.cdd.gsl.domain.FollowInfoDomain;
import com.cdd.gsl.domain.ThirdUserInfoDomain;
import com.cdd.gsl.vo.*;

import java.util.List;

public interface UserService {
    CommonResult thirdLogin(ThirdUserInfoDomain thirdUserInfoDomain);

    CommonResult register(UserInfoVo userInfoVo);

    CommonResult checkPhone(String phone);

    CommonResult forgetPassword(UserInfoVo userInfoVo);

    CommonResult<LoginTokenVo> login(LoginUserVo loginUserVo);

    CommonResult agreeCompany(Long userId,Long companyId);

    CommonResult bindPhone(Long userId,String phone);

    CommonResult findFollow(FollowConditionVo followConditionVo);

    CommonResult followInfo(FollowInfoDomain followInfoDomain);

    CommonResult cancelFollow(FollowInfoVo followInfoVo);

    CommonResult isFollow(IsFollowVo isFollowVo);

    List<CommonDictDomain> findDictInfo(String dictName);

    CommonResult authenticationBroker(ApplyBrokerInfoVo applyBrokerInfoDomain);

    CommonResult findBroker(Long userId);

    CommonResult agreeBroker(Long brokerId);

    CommonResult<String> verifyCode(String phone);

    CommonResult changeDevice(ChangeDeviceVo changeDeviceVo);

    CommonResult<SingleUserInfoVo> findUserInfo(Long userId);

    CommonResult findApplyBroker(Long userId);

    CommonResult companyTeam(Long userId,Integer userType);

    CommonResult updateUser(UserParamVo userParamVo);

    CommonResult allBroker(Integer pageNo,Integer pageSize);

    CommonResult home();

    CommonResult brokerDetail(Long userId);



}
