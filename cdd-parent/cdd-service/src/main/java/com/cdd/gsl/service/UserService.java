package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
    CommonResult thirdLogin(ThirdUserInfoDomain thirdUserInfoDomain);

    CommonResult register(UserInfoVo userInfoVo);

    CommonResult checkPhone(String phone);

    CommonResult forgetPassword(UserInfoVo userInfoVo);

    CommonResult<LoginTokenVo> login(LoginUserVo loginUserVo);

    CommonResult logout(String phone);

    CommonResult agreeCompany(Long userId,Long companyId);

    CommonResult bindPhone(Long userId,String phone);

    CommonResult findFollow(FollowConditionVo followConditionVo);

    CommonResult followInfo(FollowInfoDomain followInfoDomain);

    CommonResult cancelFollow(FollowInfoVo followInfoVo);

    CommonResult isFollow(IsFollowVo isFollowVo);

    List<CommonDictDomain> findDictInfo(String dictName);

    CommonResult authenticationBroker(ApplyBrokerInfoVo applyBrokerInfoDomain);

    CommonResult findBroker(Long userId);

    CommonResult findBrokerDetail(Long userId);

    CommonResult agreeBroker(Long brokerId);

    CommonResult<String> verifyCode(String phone);

    CommonResult changeDevice(ChangeDeviceVo changeDeviceVo);

    CommonResult<SingleUserInfoVo> findUserInfo(Long userId);

    CommonResult findApplyBroker(Long userId);

    CommonResult companyTeam(Long userId,Integer userType);

    CommonResult updateUser(UserParamVo userParamVo);

    CommonResult allBroker(Integer pageNo,Integer pageSize,String param);

    CommonResult home();

    CommonResult brokerDetail(Long userId);

    CommonResult updateBroker(ApplyBrokerInfoDomain applyBrokerInfoDomain);

    CommonResult messageList(MessageConditionVo messageConditionVo);

    CommonResult messageDetail(Long messageId);

    CommonResult messageUnreadCount(Long userId);

    CommonResult checkMobile(CheckPhoneDomain checkPhoneDomain);

    CommonResult buyCurrency(UserCurrencyMappingDomain userCurrencyMappingDomain);

    CommonResult slideList(String city);

    CommonResult createLeague(LeagueInfoParamVo leagueInfoParamVo);

    /****************************管理员**********************************/
    CommonResult userList(UserAdminConditionVo userConditionVo);

    CommonResult deleteUser(Long userId);

    CommonResult recoverUser(Long userId);

    CommonResult addUser(UserInfoDomain userInfoDomain);

    CommonResult updateCurrency(UserInfoDomain userInfoDomain);

    CommonResult createSearchCity(SearchCityUserInfo searchCityUserInfo);
}
