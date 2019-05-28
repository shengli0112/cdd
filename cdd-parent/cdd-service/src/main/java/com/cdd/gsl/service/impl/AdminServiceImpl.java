package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.*;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.vo.AdminRoleVo;
import com.cdd.gsl.vo.ApplyBrokerConditionVo;
import com.cdd.gsl.vo.ApplyBrokerInfoVo;
import com.cdd.gsl.vo.MenuInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminInfoDao adminInfoDao;

    @Autowired
    private AdminTicketDomainMapper adminTicketDomainMapper;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private UserInfoDomainMapper userInfoDomainMapper;

    @Override
    public CommonResult doLogin(String username, String password) throws Exception {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            List<AdminInfoDomain> adminInfoDomains = adminInfoDao.selectAdminByUsernameAndPassword(username,password);
            if(adminInfoDomains != null && adminInfoDomains.size() > 0){
                AdminInfoDomain adminInfoDomain = adminInfoDomains.get(0);
                String waitToken = adminInfoDomain.getId() + adminInfoDomain.getSlat()+System.currentTimeMillis();
                String token = DigestUtils.md5DigestAsHex(waitToken.getBytes());
                AdminTicketDomain userTicketDomain = new AdminTicketDomain();
                userTicketDomain.setAdminId(adminInfoDomain.getId());
                userTicketDomain.setToken(token);
                adminTicketDomainMapper.insert(userTicketDomain);
                JSONObject tokenJson = new JSONObject();
                tokenJson.put("token",token);
                commonResult.setFlag(1);
                commonResult.setMessage("登录成功");
                commonResult.setData(tokenJson);
            }else{
                commonResult.setFlag(0);
                commonResult.setMessage("没有对应的用户");
            }
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("参数不正确");
        }

        return commonResult;
    }

    @Override
    public String getPasswordByUserName(String username) {
        return adminInfoDao.selectPasswordByUsername(username);
    }

    @Override
    public List<MenuInfoVo> getPermissionByUserName(String username) {
        return adminInfoDao.selectMenuListByUsername(username);
    }

    @Override
    public AdminInfoDomain getAdminByUsernameAndPassword(String username, String password) {
        List<AdminInfoDomain> adminInfoDomains = adminInfoDao.selectAdminByUsernameAndPassword(username,password);
        AdminInfoDomain adminInfoDomain = new AdminInfoDomain();
        if(adminInfoDomains != null && adminInfoDomains.size() > 0){
            adminInfoDomain = adminInfoDomains.get(0);
        }
        return adminInfoDomain;
    }

    @Override
    public CommonResult info(String token) {
        CommonResult commonResult = new CommonResult();
        if(!StringUtils.isEmpty(token)){
            AdminTicketDomainExample adminTicketDomainExample = new AdminTicketDomainExample();
            adminTicketDomainExample.createCriteria().andTokenEqualTo(token);
            List<AdminTicketDomain> adminTicketDomainList = adminTicketDomainMapper.selectByExample(adminTicketDomainExample);

            if(adminTicketDomainList != null && adminTicketDomainList.size() > 0){
                AdminTicketDomain adminTicketDomain = adminTicketDomainList.get(0);
                AdminRoleVo adminRoleVo = adminInfoDao.selectUserInfoByUserId(adminTicketDomain.getAdminId());
                commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
                commonResult.setMessage("查询成功");
                commonResult.setData(adminRoleVo);
            }else{
                commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
                commonResult.setMessage("该token不合法");
            }
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("token不能为空");
        }


        return commonResult;
    }

    @Override
    public CommonResult brokerList(ApplyBrokerConditionVo applyBrokerConditionVo) {
        CommonResult commonResult = new CommonResult();
        if(applyBrokerConditionVo != null){
            int count = applyBrokerInfoDao.managerBrokerCount();
            List<ApplyBrokerInfoVo> applyBrokerInfoVos = applyBrokerInfoDao.managerBrokerList(applyBrokerConditionVo);
            JSONObject data = new JSONObject();
            data.put("total",count);
            data.put("items",applyBrokerInfoVos);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(data);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不完整");
        }
        return commonResult;
    }

    @Override
    public CommonResult passAudit(ApplyBrokerInfoDomain applyBrokerInfoDomain) {
        CommonResult commonResult = new CommonResult();
        if( applyBrokerInfoDomain != null ){
            applyBrokerInfoDomainMapper.updateByPrimaryKeySelective(applyBrokerInfoDomain);
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setId(applyBrokerInfoDomain.getUserId());
            userInfoDomain.setUserType(2);
            userInfoDomainMapper.updateByPrimaryKeySelective(userInfoDomain);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("审核通过");
        }else{
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("参数不完整");
        }

        return commonResult;
    }
}