package com.cdd.gsl.service;

import com.cdd.gsl.admin.UserBrokerConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.UserBrokerInfoDomain;
import org.springframework.web.multipart.MultipartFile;

public interface UserBrokerService {
    public CommonResult importUserBroker(MultipartFile file);

    public CommonResult findUserBroker(UserBrokerConditionVo userBrokerConditionVo);

    CommonResult updateUserBroker(UserBrokerInfoDomain userBrokerInfoDomain);

    CommonResult addUserBroker(UserBrokerInfoDomain userBrokerInfoDomain);
}
