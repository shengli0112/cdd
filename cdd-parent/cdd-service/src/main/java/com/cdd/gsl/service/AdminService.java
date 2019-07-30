package com.cdd.gsl.service;

import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.AdminInfoDomain;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.InformHouseRecordDomain;
import com.cdd.gsl.vo.AdminInformInfoConditionVo;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.MenuInfoVo;

import java.util.List;

public interface AdminService {
    public CommonResult doLogin(String username, String password) throws Exception;

    public String getPasswordByUserName(String username);

    public List<MenuInfoVo> getPermissionByUserName(String username);

    AdminInfoDomain getAdminByUsernameAndPassword(String username, String password);

    CommonResult info(String token);

    CommonResult createAdmin(AdminInfoDomain adminInfoDomain);

    CommonResult roleList();

    CommonResult adminList();

    /*********************店长*****************************/
    CommonResult brokerList(ApplyBrokerConditionVo applyBrokerConditionVo);

    CommonResult companyBrokerList(String companyName);

    CommonResult companyUserList(String companyName);

    CommonResult passAudit(ApplyBrokerInfoDomain applyBrokerInfoDomain);

    /*******************房源******************************/
    CommonResult findHouseList(HouseAdminConditionVo houseConditionVo);

    /***************举报*****************************/
    public CommonResult findInformList(AdminInformInfoConditionVo adminInformInfoConditionVo);

    public void handlerInform(InformHouseRecordDomain informHouseRecordDomain);
}
